package com.nekyoh.epiccraft.client.render;

import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;

/**
 * Renderer para mobs que usam uma skin de jogador 64x64 (com as camadas de jaqueta,
 * mangas, calça e chapéu). Usado pelo The Inventor e pelo Vampire King.
 * O tamanho maior do Vampire King vem do atributo SCALE, aplicado automaticamente.
 */
public class SkinnedHumanoidRenderer<T extends Mob> extends HumanoidMobRenderer<T, PlayerModel<T>> {
    private final ResourceLocation texture;

    public SkinnedHumanoidRenderer(EntityRendererProvider.Context context, ResourceLocation texture) {
        super(context, new PlayerModel<>(context.bakeLayer(ModelLayers.PLAYER), false), 0.5F);
        this.texture = texture;
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return texture;
    }
}
