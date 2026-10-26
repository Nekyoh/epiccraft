package com.nekyoh.epiccraft.common.item.properties;

import com.nekyoh.epiccraft.EpicCraft;
import com.nekyoh.epiccraft.common.item.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

/**
 * Na 1.21 o ArmorMaterial virou um registro (não é mais enum).
 * Textura: assets/epiccraft/textures/models/armor/<nome>_layer_1.png / _layer_2.png
 *
 * A durabilidade agora fica no Item.Properties (veja ModItems#armor), por isso
 * guardamos o multiplicador aqui só como constante.
 */
public final class ModArmorMaterials {
    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS =
            DeferredRegister.create(Registries.ARMOR_MATERIAL, EpicCraft.MODID);

    //                                                                       botas calça peito capacete
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> EMERALD = register("emerald", 3, 6, 8, 3, 10, SoundEvents.ARMOR_EQUIP_DIAMOND, 2.0F, 0.0F, () -> Ingredient.of(Items.EMERALD));
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> AMETHYST = register("amethyst", 3, 6, 8, 3, 10, SoundEvents.ARMOR_EQUIP_DIAMOND, 2.0F, 0.0F, () -> Ingredient.of(Items.AMETHYST_CLUSTER));
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> RUBY = register("ruby", 3, 6, 8, 3, 10, SoundEvents.ARMOR_EQUIP_DIAMOND, 2.0F, 0.0F, () -> Ingredient.of(ModItems.RUBY.get()));
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> SAPHIRE = register("saphire", 3, 6, 8, 3, 10, SoundEvents.ARMOR_EQUIP_DIAMOND, 2.0F, 0.0F, () -> Ingredient.of(ModItems.SAPHIRE.get()));
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> NEZONITA = register("nezonita", 4, 7, 9, 4, 20, SoundEvents.ARMOR_EQUIP_NETHERITE, 4.0F, 0.2F, () -> Ingredient.of(ModItems.NEZONITA_INGOT.get()));
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> TEKYNITA = register("tekynita", 4, 7, 9, 4, 20, SoundEvents.ARMOR_EQUIP_NETHERITE, 4.0F, 0.2F, () -> Ingredient.of(ModItems.TEKYNITA_INGOT.get()));
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> COPPER = register("copper", 1, 3, 4, 2, 9, SoundEvents.ARMOR_EQUIP_IRON, 0.0F, 0.0F, () -> Ingredient.of(Items.COPPER_INGOT));
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> PINK_IRON = register("pink_iron", 2, 5, 6, 2, 9, SoundEvents.ARMOR_EQUIP_IRON, 0.0F, 0.0F, () -> Ingredient.of(ModItems.PINK_IRON_INGOT.get()));

    // Multiplicadores de durabilidade (mesmos valores do mod original)
    public static final int EMERALD_DURABILITY = 33;
    public static final int AMETHYST_DURABILITY = 33;
    public static final int RUBY_DURABILITY = 33;
    public static final int SAPHIRE_DURABILITY = 33;
    public static final int NEZONITA_DURABILITY = 45;
    public static final int TEKYNITA_DURABILITY = 45;
    public static final int COPPER_DURABILITY = 11;
    public static final int PINK_IRON_DURABILITY = 15;

    private static DeferredHolder<ArmorMaterial, ArmorMaterial> register(
            String name, int boots, int leggings, int chestplate, int helmet,
            int enchantmentValue, Holder<SoundEvent> equipSound,
            float toughness, float knockbackResistance, Supplier<Ingredient> repair) {

        EnumMap<ArmorItem.Type, Integer> defense = new EnumMap<>(ArmorItem.Type.class);
        defense.put(ArmorItem.Type.BOOTS, boots);
        defense.put(ArmorItem.Type.LEGGINGS, leggings);
        defense.put(ArmorItem.Type.CHESTPLATE, chestplate);
        defense.put(ArmorItem.Type.HELMET, helmet);
        defense.put(ArmorItem.Type.BODY, chestplate);

        return ARMOR_MATERIALS.register(name, () -> new ArmorMaterial(
                defense, enchantmentValue, equipSound, repair,
                List.of(new ArmorMaterial.Layer(EpicCraft.id(name))),
                toughness, knockbackResistance));
    }

    /** true se o item é uma peça de armadura desse material. */
    public static boolean is(ItemStack stack, DeferredHolder<ArmorMaterial, ArmorMaterial> material) {
        return !stack.isEmpty()
                && stack.getItem() instanceof ArmorItem armor
                && armor.getMaterial().is(material.getKey());
    }

    private ModArmorMaterials() {}
}
