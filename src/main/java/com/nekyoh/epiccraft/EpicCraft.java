package com.nekyoh.epiccraft;

import com.mojang.logging.LogUtils;
import com.nekyoh.epiccraft.common.block.ModBlocks;
import com.nekyoh.epiccraft.common.effect.ModEffects;
import com.nekyoh.epiccraft.common.entity.ModEntities;
import com.nekyoh.epiccraft.common.item.ModItems;
import com.nekyoh.epiccraft.common.item.ModTabs;
import com.nekyoh.epiccraft.common.item.properties.ModArmorMaterials;
import com.nekyoh.epiccraft.common.loot.ModLootModifiers;
import com.nekyoh.epiccraft.config.Config;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.DispenserBlock;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;

@Mod(EpicCraft.MODID)
public class EpicCraft {
    public static final String MODID = "epiccraft";
    public static final Logger LOGGER = LogUtils.getLogger();

    public EpicCraft(IEventBus modEventBus, ModContainer modContainer) {
        // Configuração (config/epiccraft-common.toml)
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC, "epiccraft-common.toml");

        // Registros (a ordem não importa, os DeferredRegister cuidam disso)
        ModArmorMaterials.ARMOR_MATERIALS.register(modEventBus);
        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModEntities.ENTITIES.register(modEventBus);
        ModEffects.MOB_EFFECTS.register(modEventBus);
        ModTabs.CREATIVE_MODE_TABS.register(modEventBus);
        ModLootModifiers.LOOT_MODIFIERS.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
    }

    /** Atalho para criar ResourceLocation com o namespace do mod. */
    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            // Dispensers conseguem atirar a flecha de obsidiana e a kunai
            DispenserBlock.registerProjectileBehavior(ModItems.OBSIDIAN_ARROW.get());
            DispenserBlock.registerProjectileBehavior(ModItems.KUNAI.get());
        });
        LOGGER.info("EpicCraft iniciado!");
    }
}
