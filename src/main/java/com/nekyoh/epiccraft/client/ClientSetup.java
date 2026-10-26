package com.nekyoh.epiccraft.client;

import com.nekyoh.epiccraft.EpicCraft;
import com.nekyoh.epiccraft.client.render.KingSlimeRenderer;
import com.nekyoh.epiccraft.client.render.ObsidianArrowRenderer;
import com.nekyoh.epiccraft.client.render.SkinnedHumanoidRenderer;
import com.nekyoh.epiccraft.client.render.ThrownWeaponRenderer;
import com.nekyoh.epiccraft.common.entity.ModEntities;
import com.nekyoh.epiccraft.common.item.ModItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = EpicCraft.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientSetup {
    private static final ResourceLocation BLOCKING = EpicCraft.id("blocking");

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        // Projéteis
        event.registerEntityRenderer(ModEntities.OBSIDIAN_ARROW.get(), ObsidianArrowRenderer::new);
        event.registerEntityRenderer(ModEntities.MJOLNIR.get(),
                context -> new ThrownWeaponRenderer<>(context, ModItems.MJOLNIR, 1.2F, true));
        event.registerEntityRenderer(ModEntities.KUNAI.get(),
                context -> new ThrownWeaponRenderer<>(context, ModItems.KUNAI, 0.8F, false));

        // Mobs com skin de jogador (64x64)
        event.registerEntityRenderer(ModEntities.THE_INVENTOR.get(),
                context -> new SkinnedHumanoidRenderer<>(context, EpicCraft.id("textures/entity/the_inventor.png")));
        event.registerEntityRenderer(ModEntities.VAMPIRE_KING.get(),
                context -> new SkinnedHumanoidRenderer<>(context, EpicCraft.id("textures/entity/vampire_king.png")));

        // Slime gigante
        event.registerEntityRenderer(ModEntities.KING_SLIME.get(), KingSlimeRenderer::new);
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            // Modelo "defendendo" (troca pelo *_blocking.json enquanto segura o botão direito)
            registerBlocking(ModItems.FRYING_PAN.get());
            registerBlocking(ModItems.KATANA.get());
            registerBlocking(ModItems.DARK_KATANA.get());
            registerBlocking(ModItems.BASEBALL_BAT.get());
        });
    }

    private static void registerBlocking(Item item) {
        ItemProperties.register(item, BLOCKING, (stack, level, entity, seed) ->
                entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F);
    }

    private ClientSetup() {}
}
