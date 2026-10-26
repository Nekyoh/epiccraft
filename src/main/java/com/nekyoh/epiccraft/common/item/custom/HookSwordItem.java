package com.nekyoh.epiccraft.common.item.custom;

import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

/**
 * Jet — espadas de gancho (hook swords / lâminas de tigre).
 * - Os golpes "enganchão" o alvo: em vez de empurrar, puxam ele para perto e deixam lento.
 * - Clique direito lança o gancho:
 *     mirando numa criatura (até 10 blocos) -> puxa ela até você;
 *     mirando num bloco (até 16 blocos)     -> você é puxado até o bloco (gancho de escalada).
 */
public class HookSwordItem extends EpicSwordItem {
    private static final double ENTITY_RANGE = 10.0D;
    private static final double BLOCK_RANGE = 16.0D;
    private static final int COOLDOWN = 30;

    public HookSwordItem(Tier tier, Item.Properties properties) {
        super(tier, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.level().isClientSide) {
            Vec3 pull = attacker.position().subtract(target.position()).multiply(1, 0, 1);
            if (pull.lengthSqr() > 1.0E-4D) {
                double resist = 1.0D - target.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
                pull = pull.normalize().scale(0.35D * resist);
                // cancela o empurrão normal e puxa o alvo para perto
                target.setDeltaMovement(target.getDeltaMovement().multiply(0.2D, 1.0D, 0.2D).add(pull.x, 0.0D, pull.z));
                target.hurtMarked = true;
            }
            target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 30, 0), attacker);
        }
        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (level instanceof ServerLevel serverLevel) {
            Vec3 eye = player.getEyePosition();
            Vec3 look = player.getLookAngle();

            // 1) Tenta enganchar uma criatura
            Vec3 entityEnd = eye.add(look.scale(ENTITY_RANGE));
            AABB searchBox = player.getBoundingBox().expandTowards(look.scale(ENTITY_RANGE)).inflate(1.0D);
            EntityHitResult entityHit = ProjectileUtil.getEntityHitResult(level, player, eye, entityEnd, searchBox,
                    e -> e instanceof LivingEntity && e.isAlive() && !e.isSpectator() && e != player);

            BlockHitResult blockHit = level.clip(new ClipContext(eye, eye.add(look.scale(BLOCK_RANGE)),
                    ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));

            boolean entityFirst = entityHit != null && (blockHit.getType() == HitResult.Type.MISS
                    || eye.distanceToSqr(entityHit.getLocation()) < eye.distanceToSqr(blockHit.getLocation()));

            if (entityFirst) {
                Entity target = entityHit.getEntity();
                Vec3 dir = eye.subtract(target.position()).normalize();
                double resist = target instanceof LivingEntity living
                        ? 1.0D - living.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE) : 1.0D;
                target.setDeltaMovement(dir.x * 1.3D * resist, 0.35D + dir.y * 0.4D * resist, dir.z * 1.3D * resist);
                target.hurtMarked = true;
                drawChain(serverLevel, eye, target.position().add(0, target.getBbHeight() * 0.5D, 0));
            } else if (blockHit.getType() == HitResult.Type.BLOCK) {
                Vec3 to = blockHit.getLocation().subtract(player.position());
                Vec3 velocity = to.normalize().scale(Math.min(1.8D, 0.6D + to.length() * 0.09D)).add(0, 0.35D, 0);
                player.setDeltaMovement(velocity);
                player.hurtMarked = true;
                player.resetFallDistance();
                drawChain(serverLevel, eye, blockHit.getLocation());
            } else {
                return InteractionResultHolder.fail(stack);
            }

            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.CHAIN_PLACE, SoundSource.PLAYERS, 1.0F, 1.4F);
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.FISHING_BOBBER_THROW, SoundSource.PLAYERS, 0.8F, 0.8F);
            stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
        }
        player.getCooldowns().addCooldown(this, COOLDOWN);
        player.swing(hand);
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    private static void drawChain(ServerLevel level, Vec3 from, Vec3 to) {
        ItemParticleOption chain = new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(Items.CHAIN));
        Vec3 step = to.subtract(from);
        int points = (int) Math.max(4, step.length() * 3);
        for (int i = 0; i <= points; i++) {
            Vec3 p = from.add(step.scale(i / (double) points));
            level.sendParticles(chain, p.x, p.y, p.z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
        }
    }
}
