package com.nekyoh.epiccraft.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

/**
 * Renderiza um projétil usando o modelo 3D do item, apontado na direção do voo.
 * Usado pelo Mjolnir (girando) e pela Kunai.
 */
public class ThrownWeaponRenderer<T extends AbstractArrow> extends EntityRenderer<T> {
    private final ItemRenderer itemRenderer;
    private final Supplier<? extends ItemLike> item;
    private final float scale;
    private final boolean spin;
    private ItemStack cachedStack = ItemStack.EMPTY;

    public ThrownWeaponRenderer(EntityRendererProvider.Context context, Supplier<? extends ItemLike> item, float scale, boolean spin) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
        this.item = item;
        this.scale = scale;
        this.spin = spin;
    }

    @Override
    public void render(T entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        if (cachedStack.isEmpty()) {
            cachedStack = new ItemStack(item.get());
        }

        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTick, entity.yRotO, entity.getYRot()) - 90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partialTick, entity.xRotO, entity.getXRot())));

        boolean flying = entity.getDeltaMovement().lengthSqr() > 0.01D;
        if (spin && flying) {
            poseStack.mulPose(Axis.ZP.rotationDegrees((entity.tickCount + partialTick) * -40.0F));
        }
        // O sprite do item é diagonal (cabo embaixo à esquerda), então giramos para a lâmina ir para frente
        poseStack.mulPose(Axis.ZP.rotationDegrees(-45.0F));
        poseStack.scale(scale, scale, scale);

        itemRenderer.renderStatic(cachedStack, ItemDisplayContext.FIXED, packedLight, OverlayTexture.NO_OVERLAY,
                poseStack, buffer, entity.level(), entity.getId());
        poseStack.popPose();

        super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
