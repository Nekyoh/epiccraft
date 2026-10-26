package com.nekyoh.epiccraft.common.item.custom;

import com.nekyoh.epiccraft.common.effect.ModEffects;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;

/** Espada de vidro: aplica sangramento e se estilhaça (durabilidade 1). */
public class GlassSwordItem extends EpicSwordItem {

    public GlassSwordItem(Tier tier, Item.Properties properties) {
        super(tier, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.level().isClientSide) {
            target.level().playSound(null, target.getX(), target.getY(), target.getZ(),
                    SoundEvents.GLASS_BREAK, SoundSource.PLAYERS, 1.0F, 1.0F);
            // visible = false: sem as espirais de poção, mas com ícone
            target.addEffect(new MobEffectInstance(ModEffects.BLEEDING, 200, 0, false, false, true), attacker);
        }
        return super.hurtEnemy(stack, target, attacker);
    }
}
