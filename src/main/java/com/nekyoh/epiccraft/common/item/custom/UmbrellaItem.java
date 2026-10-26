package com.nekyoh.epiccraft.common.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

/** Guarda-chuva: segurando em qualquer mão, você plana devagar e não toma dano de queda. */
public class UmbrellaItem extends Item {
    private static final double MAX_FALL_SPEED = -0.12D;

    public UmbrellaItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (!(entity instanceof LivingEntity living)) return;
        if (living.getMainHandItem() != stack && living.getOffhandItem() != stack) return;
        if (living.onGround() || living.isInWater() || living.isInLava() || living.isFallFlying()) return;
        if (living instanceof Player player && player.getAbilities().flying) return;

        Vec3 motion = living.getDeltaMovement();
        if (motion.y < MAX_FALL_SPEED) {
            living.setDeltaMovement(motion.x, MAX_FALL_SPEED, motion.z);
        }
        living.resetFallDistance();
    }

    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(1, attacker, EquipmentSlot.MAINHAND);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        EpicSwordItem.addAbilityTooltip(this, tooltip);
    }
}
