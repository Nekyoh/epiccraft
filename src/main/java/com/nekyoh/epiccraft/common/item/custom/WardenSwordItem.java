package com.nekyoh.epiccraft.common.item.custom;

import com.nekyoh.epiccraft.common.util.CombatUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/**
 * Warden Sword (drop do Warden) — os ataques do Warden:
 * - Golpes aplicam Escuridão (Darkness).
 * - Segure o botão direito ~1,5s e solte para disparar o Sonic Boom:
 *   um raio sônico que atravessa armadura e acerta tudo na linha (16 blocos).
 */
public class WardenSwordItem extends EpicSwordItem {
    private static final int CHARGE_TICKS = 30;
    private static final int COOLDOWN = 100;
    private static final double RANGE = 16.0D;
    private static final float BOOM_DAMAGE = 12.0F;

    public WardenSwordItem(Tier tier, Item.Properties properties) {
        super(tier, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.level().isClientSide) {
            target.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 120, 0), attacker);
        }
        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.SPEAR;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        player.startUsingItem(hand);
        if (!level.isClientSide) {
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.WARDEN_SONIC_CHARGE, SoundSource.PLAYERS, 1.5F, 1.0F);
        }
        return InteractionResultHolder.consume(player.getItemInHand(hand));
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeLeft) {
        int charged = this.getUseDuration(stack, entity) - timeLeft;
        if (charged < CHARGE_TICKS || !(level instanceof ServerLevel serverLevel)) return;

        sonicBoom(serverLevel, entity);
        if (entity instanceof Player player) {
            player.getCooldowns().addCooldown(this, COOLDOWN);
        }
        stack.hurtAndBreak(5, entity, LivingEntity.getSlotForHand(entity.getUsedItemHand()));
    }

    private static void sonicBoom(ServerLevel level, LivingEntity user) {
        Vec3 start = user.getEyePosition().subtract(0, 0.2D, 0);
        Vec3 dir = user.getLookAngle();

        for (int i = 1; i <= (int) RANGE; i++) {
            Vec3 p = start.add(dir.scale(i));
            level.sendParticles(ParticleTypes.SONIC_BOOM, p.x, p.y, p.z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
        }
        level.playSound(null, user.getX(), user.getY(), user.getZ(),
                SoundEvents.WARDEN_SONIC_BOOM, SoundSource.PLAYERS, 3.0F, 1.0F);

        AABB box = user.getBoundingBox().expandTowards(dir.scale(RANGE)).inflate(1.5D);
        for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, box)) {
            if (!CombatUtil.canAreaHit(user, target)) continue;
            Vec3 toTarget = target.getBoundingBox().getCenter().subtract(start);
            double along = toTarget.dot(dir);
            if (along < 0 || along > RANGE) continue;
            double offLine = toTarget.subtract(dir.scale(along)).length();
            if (offLine > 1.2D + target.getBbWidth() * 0.5D) continue;

            if (target.hurt(level.damageSources().sonicBoom(user), BOOM_DAMAGE)) {
                double vertical = 0.5D * (1.0D - target.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
                double horizontal = 2.5D * (1.0D - target.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
                target.push(dir.x * horizontal, dir.y * vertical + 0.1D, dir.z * horizontal);
                target.hurtMarked = true;
            }
        }
    }
}
