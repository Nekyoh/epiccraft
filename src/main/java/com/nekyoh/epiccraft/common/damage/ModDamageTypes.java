package com.nekyoh.epiccraft.common.damage;

import com.nekyoh.epiccraft.EpicCraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.level.Level;

public final class ModDamageTypes {
    /** data/epiccraft/damage_type/bleeding.json */
    public static final ResourceKey<DamageType> BLEEDING =
            ResourceKey.create(Registries.DAMAGE_TYPE, EpicCraft.id("bleeding"));

    public static DamageSource bleeding(Level level) {
        return new DamageSource(level.registryAccess()
                .registryOrThrow(Registries.DAMAGE_TYPE)
                .getHolderOrThrow(BLEEDING));
    }

    private ModDamageTypes() {}
}
