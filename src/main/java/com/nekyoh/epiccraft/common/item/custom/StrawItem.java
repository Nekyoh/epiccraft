package com.nekyoh.epiccraft.common.item.custom;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class StrawItem extends Item {
    public StrawItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
        if (!(target instanceof Turtle)) {
            return InteractionResult.PASS;
        }
        // Já envenenada: ignora e não gasta o item
        if (target.hasEffect(MobEffects.POISON)) {
            return InteractionResult.PASS;
        }

        if (player.level() instanceof ServerLevel serverLevel) {
            if (target.getHealth() <= 1.0F) {
                target.addEffect(new MobEffectInstance(MobEffects.HARM, 1, 0), player);
                serverLevel.playSound(null, target.getX(), target.getY(), target.getZ(),
                        SoundEvents.TURTLE_DEATH, SoundSource.PLAYERS, 1.0F, 1.0F);
            } else {
                target.addEffect(new MobEffectInstance(MobEffects.POISON, 200, 1), player);
                serverLevel.playSound(null, target.getX(), target.getY(), target.getZ(),
                        SoundEvents.BUBBLE_COLUMN_WHIRLPOOL_INSIDE, SoundSource.PLAYERS, 1.0F, 1.5F);
                serverLevel.sendParticles(ParticleTypes.BUBBLE,
                        target.getX(), target.getY(0.5D), target.getZ(),
                        15, 0.3D, 0.3D, 0.3D, 0.1D);
            }
            stack.consume(1, player);
        }
        return InteractionResult.sidedSuccess(player.level().isClientSide());
    }
}
