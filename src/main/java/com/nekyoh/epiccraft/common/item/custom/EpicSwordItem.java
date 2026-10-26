package com.nekyoh.epiccraft.common.item.custom;

import com.nekyoh.epiccraft.EpicCraft;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

/**
 * Base de todas as espadas especiais do mod.
 * Adiciona automaticamente a descrição da habilidade no tooltip, lendo do arquivo de idioma:
 *   tooltip.epiccraft.<nome_do_item>     (linha 1)
 *   tooltip.epiccraft.<nome_do_item>.2   (linha 2, opcional)
 */
public class EpicSwordItem extends SwordItem {

    public EpicSwordItem(Tier tier, Item.Properties properties) {
        super(tier, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        addAbilityTooltip(this, tooltip);
    }

    public static void addAbilityTooltip(Item item, List<Component> tooltip) {
        ResourceLocation id = BuiltInRegistries.ITEM.getKey(item);
        if (!EpicCraft.MODID.equals(id.getNamespace())) return;
        String key = "tooltip." + EpicCraft.MODID + "." + id.getPath();
        Language language = Language.getInstance();
        if (language.has(key)) {
            tooltip.add(Component.translatable(key).withStyle(ChatFormatting.GRAY));
        }
        if (language.has(key + ".2")) {
            tooltip.add(Component.translatable(key + ".2").withStyle(ChatFormatting.GRAY));
        }
    }
}
