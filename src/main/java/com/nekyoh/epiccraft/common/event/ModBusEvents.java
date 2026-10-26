package com.nekyoh.epiccraft.common.event;

import com.nekyoh.epiccraft.EpicCraft;
import com.nekyoh.epiccraft.common.entity.ModEntities;
import com.nekyoh.epiccraft.common.entity.mob.KingSlime;
import com.nekyoh.epiccraft.common.entity.mob.TheInventor;
import com.nekyoh.epiccraft.common.entity.mob.VampireKing;
import com.nekyoh.epiccraft.common.item.ModItems;
import com.nekyoh.epiccraft.network.PlayNotePayload;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

/** Eventos do barramento do mod: atributos dos mobs, regras de spawn, pacotes de rede. */
@EventBusSubscriber(modid = EpicCraft.MODID, bus = EventBusSubscriber.Bus.MOD)
public final class ModBusEvents {

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.THE_INVENTOR.get(), TheInventor.createAttributes().build());
        event.put(ModEntities.VAMPIRE_KING.get(), VampireKing.createAttributes().build());
        event.put(ModEntities.KING_SLIME.get(), KingSlime.createAttributes().build());
    }

    @SubscribeEvent
    public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        event.register(ModEntities.THE_INVENTOR.get(), SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ModBusEvents::checkModMonsterSpawn,
                RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(ModEntities.VAMPIRE_KING.get(), SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ModBusEvents::checkModMonsterSpawn,
                RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(ModEntities.KING_SLIME.get(), SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, KingSlime::checkKingSlimeSpawnRules,
                RegisterSpawnPlacementsEvent.Operation.REPLACE);
    }

    /**
     * Regra normal de monstro (escuro, fora do pacífico), mas nunca na Ilha de Cogumelos
     * nem no Deep Dark, que no vanilla não têm monstros.
     */
    private static boolean checkModMonsterSpawn(EntityType<? extends Monster> type, ServerLevelAccessor level,
                                                MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        if (spawnType == MobSpawnType.NATURAL) {
            var biome = level.getBiome(pos);
            if (biome.is(Biomes.MUSHROOM_FIELDS) || biome.is(Biomes.DEEP_DARK)) return false;
        }
        return Monster.checkMonsterSpawnRules(type, level, spawnType, pos, random);
    }

    @SubscribeEvent
    public static void registerPayloads(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");
        registrar.playToServer(PlayNotePayload.TYPE, PlayNotePayload.STREAM_CODEC, PlayNotePayload::handle);
    }

    @SubscribeEvent
    public static void addToVanillaTabs(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
            event.accept(ModItems.THE_INVENTOR_SPAWN_EGG.get());
            event.accept(ModItems.VAMPIRE_KING_SPAWN_EGG.get());
            event.accept(ModItems.KING_SLIME_SPAWN_EGG.get());
        }
    }

    private ModBusEvents() {}
}
