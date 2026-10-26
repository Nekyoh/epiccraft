package com.nekyoh.epiccraft.client.render;

import com.nekyoh.epiccraft.EpicCraft;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.SlimeRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Slime;

/** Usa o modelo do slime vanilla com a textura do King Slime (o tamanho vem do "size" 8). */
public class KingSlimeRenderer extends SlimeRenderer {
    private static final ResourceLocation TEXTURE = EpicCraft.id("textures/entity/king_slime.png");

    public KingSlimeRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(Slime entity) {
        return TEXTURE;
    }
}
