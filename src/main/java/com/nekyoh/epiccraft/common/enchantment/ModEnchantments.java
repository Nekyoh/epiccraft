package com.nekyoh.epiccraft.common.enchantment;

import com.nekyoh.epiccraft.EpicCraft;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.Optional;

/**
 * Na 1.21 os encantamentos são data-driven:
 * a definição fica em data/epiccraft/enchantment/emerald_grace.json.
 * Aqui guardamos só a chave para buscar o Holder em tempo de execução.
 */
public final class ModEnchantments {
    public static final ResourceKey<Enchantment> EMERALD_GRACE =
            ResourceKey.create(Registries.ENCHANTMENT, EpicCraft.id("emerald_grace"));

    public static Optional<Holder.Reference<Enchantment>> get(HolderLookup.Provider registries, ResourceKey<Enchantment> key) {
        return registries.lookup(Registries.ENCHANTMENT).flatMap(lookup -> lookup.get(key));
    }

    private ModEnchantments() {}
}
