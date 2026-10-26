package com.nekyoh.epiccraft.common.item.custom;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/**
 * Blaze Sword:
 * - Golpes incendeiam o alvo (5s).
 * - Clique direito solta uma rajada de 3 bolas de fogo igual ao Blaze.
 */
public class BlazeSwordItem extends EpicSwordItem {
    private static final int COOLDOWN = 40;

    public BlazeSwordItem(Tier tier, Item.Properties properties) {
        super(tier, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker.level() instanceof ServerLevel level) {
            target.igniteForSeconds(5.0F);
            level.sendParticles(ParticleTypes.FLAME, target.getX(), target.getY(0.5D), target.getZ(),
                    12, 0.25D, 0.4D, 0.25D, 0.02D);
        }
        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide) {
            Vec3 look = player.getLookAngle();
            for (int i = 0; i < 3; i++) {
                // Espalha um pouco como o Blaze
                Vec3 dir = look.add(
                        player.getRandom().triangle(0.0D, 0.12D),
                        player.getRandom().triangle(0.0D, 0.06D),
                        player.getRandom().triangle(0.0D, 0.12D)).normalize();
                SmallFireball fireball = new SmallFireball(level, player, dir);
                fireball.setPos(player.getX() + look.x * 0.8D, player.getEyeY() - 0.2D, player.getZ() + look.z * 0.8D);
                level.addFreshEntity(fireball);
            }
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.BLAZE_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F);
            stack.hurtAndBreak(2, player, LivingEntity.getSlotForHand(hand));
        }
        player.getCooldowns().addCooldown(this, COOLDOWN);
        player.swing(hand);
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }
}
