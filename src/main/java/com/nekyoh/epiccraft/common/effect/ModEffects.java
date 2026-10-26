package com.nekyoh.epiccraft.common.effect;

import com.nekyoh.epiccraft.EpicCraft;
import com.nekyoh.epiccraft.common.effect.custom.BleedingEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(Registries.MOB_EFFECT, EpicCraft.MODID);

    // DeferredHolder já é um Holder<MobEffect>, então pode ir direto no MobEffectInstance
    public static final DeferredHolder<MobEffect, BleedingEffect> BLEEDING =
            MOB_EFFECTS.register("bleeding", BleedingEffect::new);

    private ModEffects() {}
}
