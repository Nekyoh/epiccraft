package com.nekyoh.epiccraft.common.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

/**
 * Armadura que mostra o bônus do conjunto no tooltip
 * (tooltip.epiccraft.set_bonus.<material> e .2).
 * Os bônus em si ficam em ArmorSetBonusHandler.
 */
public class SetBonusArmorItem extends ArmorItem {
    private final String setName;

    public SetBonusArmorItem(Holder<ArmorMaterial> material, Type type, Item.Properties properties, String setName) {
        super(material, type, properties);
        this.setName = setName;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        String key = "tooltip.epiccraft.set_bonus." + setName;
        Language language = Language.getInstance();
        if (language.has(key)) {
            tooltip.add(Component.translatable("tooltip.epiccraft.set_bonus").withStyle(ChatFormatting.GOLD));
            tooltip.add(Component.translatable(key).withStyle(ChatFormatting.GRAY));
            if (language.has(key + ".2")) {
                tooltip.add(Component.translatable(key + ".2").withStyle(ChatFormatting.GRAY));
            }
        }
    }
}
