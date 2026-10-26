package com.nekyoh.epiccraft.common.item.custom;

import com.nekyoh.epiccraft.common.effect.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;

/** Espada de roseira: lentidão nos espinhos e 30% de chance de sangramento. */
public class RoseBushSwordItem extends EpicSwordItem {

    public RoseBushSwordItem(Tier tier, Item.Properties properties) {
        super(tier, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.level().isClientSide) {
            target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 3), attacker);
            if (attacker.getRandom().nextFloat() < 0.3F) {
                target.addEffect(new MobEffectInstance(ModEffects.BLEEDING, 60, 0, false, false, true), attacker);
            }
        }
        return super.hurtEnemy(stack, target, attacker);
    }
}
