package com.nekyoh.epiccraft.common.item.custom;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;

import java.util.List;

/**
 * Espada que aplica efeitos no alvo a cada golpe (com chance opcional).
 * Usada pela Wither Rose Sword, Snake Sword, Guardian Sword, Dark Katana, etc.
 */
public class EffectSwordItem extends EpicSwordItem {

    /** Efeito aplicado ao acertar. chance = 1.0 sempre aplica. */
    public record OnHit(Holder<MobEffect> effect, int duration, int amplifier, float chance) {
        public static OnHit always(Holder<MobEffect> effect, int duration, int amplifier) {
            return new OnHit(effect, duration, amplifier, 1.0F);
        }
    }

    private final List<OnHit> effects;

    public EffectSwordItem(Tier tier, Item.Properties properties, List<OnHit> effects) {
        super(tier, properties);
        this.effects = effects;
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.level().isClientSide) {
            for (OnHit onHit : effects) {
                if (onHit.chance() >= 1.0F || attacker.getRandom().nextFloat() < onHit.chance()) {
                    target.addEffect(new MobEffectInstance(onHit.effect(), onHit.duration(), onHit.amplifier()), attacker);
                }
            }
        }
        return super.hurtEnemy(stack, target, attacker);
    }
}
