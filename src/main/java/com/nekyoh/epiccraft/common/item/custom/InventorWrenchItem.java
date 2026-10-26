package com.nekyoh.epiccraft.common.item.custom;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;

/**
 * Inventor Wrench — a arma que o The Inventor carrega.
 * - 25% de chance de dar um choque: Lentidão III + Fraqueza por 2s com faíscas.
 * - Clique direito num bloco gira ele, igual a chave inglesa comum.
 */
public class InventorWrenchItem extends EpicSwordItem {

    public InventorWrenchItem(Tier tier, Item.Properties properties) {
        super(tier, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker.level() instanceof ServerLevel level && attacker.getRandom().nextFloat() < 0.25F) {
            shock(level, target, attacker);
        }
        return super.hurtEnemy(stack, target, attacker);
    }

    public static void shock(ServerLevel level, LivingEntity target, LivingEntity source) {
        target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 2), source);
        target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 40, 0), source);
        level.sendParticles(ParticleTypes.ELECTRIC_SPARK, target.getX(), target.getY(0.5D), target.getZ(),
                25, 0.3D, 0.5D, 0.3D, 0.2D);
        level.playSound(null, target.getX(), target.getY(), target.getZ(),
                SoundEvents.LIGHTNING_BOLT_IMPACT, SoundSource.PLAYERS, 0.4F, 1.8F);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        return WrenchItem.tryRotate(context);
    }
}
