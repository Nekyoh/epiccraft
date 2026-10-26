package com.nekyoh.epiccraft.client.render;

import com.nekyoh.epiccraft.EpicCraft;
import com.nekyoh.epiccraft.common.entity.projectile.ObsidianArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class ObsidianArrowRenderer extends ArrowRenderer<ObsidianArrowEntity> {
    public static final ResourceLocation TEXTURE =
            EpicCraft.id("textures/entity/projectiles/projectile_obsidian_arrow.png");

    public ObsidianArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(ObsidianArrowEntity entity) {
        return TEXTURE;
    }
}
