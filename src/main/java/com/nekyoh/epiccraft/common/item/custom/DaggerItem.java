package com.nekyoh.epiccraft.common.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

/**
 * Adaga: arma rápida e de dano menor.
 * - Com uma adaga em cada mão, o golpe soma o dano das duas (veja CombatEvents#updateDualWield).
 * - Acertar pelas costas causa dano extra (backstab).
 * As adagas são identificadas pela tag epiccraft:daggers.
 */
public class DaggerItem extends EpicSwordItem {

    /** Dano extra padrão de adaga (somado ao bônus do tier) e velocidade de ataque. */
    public static final int BASE_DAMAGE = 1;
    public static final float ATTACK_SPEED = -2.0F;

    public DaggerItem(Tier tier, Item.Properties properties) {
        super(tier, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.epiccraft.dagger").withStyle(ChatFormatting.DARK_GRAY));
        tooltip.add(Component.translatable("tooltip.epiccraft.dagger.2").withStyle(ChatFormatting.DARK_GRAY));
    }
}
