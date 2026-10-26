package com.nekyoh.epiccraft.common.util;

import com.nekyoh.epiccraft.EpicCraft;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.component.ItemAttributeModifiers;

/** Atalhos para montar os atributos dos itens no formato da 1.21. */
public final class ItemAttributes {
    // Mesmos IDs que o vanilla usa (assim o tooltip mostra "X Dano de ataque" em verde)
    public static final ResourceLocation BASE_ATTACK_DAMAGE_ID = ResourceLocation.withDefaultNamespace("base_attack_damage");
    public static final ResourceLocation BASE_ATTACK_SPEED_ID = ResourceLocation.withDefaultNamespace("base_attack_speed");

    /**
     * Arma corpo a corpo simples (itens que não são espadas).
     * @param damage dano EXTRA (o jogador já tem 1 de base). Ex: 3.0 = 4 de dano total.
     * @param speed  modificador de velocidade (o jogador tem 4.0 de base). Ex: -2.4 = 1.6 ataques/s.
     */
    public static ItemAttributeModifiers melee(double damage, double speed) {
        return ItemAttributeModifiers.builder()
                .add(Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(BASE_ATTACK_DAMAGE_ID, damage, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_SPEED,
                        new AttributeModifier(BASE_ATTACK_SPEED_ID, speed, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND)
                .build();
    }

    /** Adiciona um atributo extra (ADD_VALUE) a modificadores existentes. */
    public static ItemAttributeModifiers with(ItemAttributeModifiers base, Holder<Attribute> attribute,
                                              String id, double amount, EquipmentSlotGroup slot) {
        return base.withModifierAdded(attribute,
                new AttributeModifier(EpicCraft.id(id), amount, AttributeModifier.Operation.ADD_VALUE), slot);
    }

    private ItemAttributes() {}
}
