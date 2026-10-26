package com.nekyoh.epiccraft.common.entity;

import com.nekyoh.epiccraft.EpicCraft;
import com.nekyoh.epiccraft.common.entity.mob.KingSlime;
import com.nekyoh.epiccraft.common.entity.mob.TheInventor;
import com.nekyoh.epiccraft.common.entity.mob.VampireKing;
import com.nekyoh.epiccraft.common.entity.projectile.KunaiEntity;
import com.nekyoh.epiccraft.common.entity.projectile.MjolnirEntity;
import com.nekyoh.epiccraft.common.entity.projectile.ObsidianArrowEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(Registries.ENTITY_TYPE, EpicCraft.MODID);

    // ---------------- Projéteis ----------------
    public static final DeferredHolder<EntityType<?>, EntityType<ObsidianArrowEntity>> OBSIDIAN_ARROW =
            ENTITIES.register("obsidian_arrow", () -> EntityType.Builder.<ObsidianArrowEntity>of(ObsidianArrowEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .eyeHeight(0.13F)
                    .clientTrackingRange(4)
                    .updateInterval(20)
                    .build("obsidian_arrow"));

    public static final DeferredHolder<EntityType<?>, EntityType<MjolnirEntity>> MJOLNIR =
            ENTITIES.register("mjolnir", () -> EntityType.Builder.<MjolnirEntity>of(MjolnirEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .eyeHeight(0.13F)
                    .clientTrackingRange(4)
                    .updateInterval(20)
                    .build("mjolnir"));

    public static final DeferredHolder<EntityType<?>, EntityType<KunaiEntity>> KUNAI =
            ENTITIES.register("kunai", () -> EntityType.Builder.<KunaiEntity>of(KunaiEntity::new, MobCategory.MISC)
                    .sized(0.3F, 0.3F)
                    .eyeHeight(0.1F)
                    .clientTrackingRange(4)
                    .updateInterval(20)
                    .build("kunai"));

    // ---------------- Mobs ----------------
    public static final DeferredHolder<EntityType<?>, EntityType<TheInventor>> THE_INVENTOR =
            ENTITIES.register("the_inventor", () -> EntityType.Builder.of(TheInventor::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.95F)
                    .eyeHeight(1.62F)
                    .clientTrackingRange(8)
                    .build("the_inventor"));

    public static final DeferredHolder<EntityType<?>, EntityType<VampireKing>> VAMPIRE_KING =
            ENTITIES.register("vampire_king", () -> EntityType.Builder.of(VampireKing::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.95F)
                    .eyeHeight(1.62F)
                    .clientTrackingRange(10)
                    .build("vampire_king"));

    // O tamanho real do slime é multiplicado pelo "size" dele (o rei usa size 8)
    public static final DeferredHolder<EntityType<?>, EntityType<KingSlime>> KING_SLIME =
            ENTITIES.register("king_slime", () -> EntityType.Builder.of(KingSlime::new, MobCategory.MONSTER)
                    .sized(0.52F, 0.52F)
                    .eyeHeight(0.325F)
                    .clientTrackingRange(10)
                    .build("king_slime"));

    private ModEntities() {}
}
