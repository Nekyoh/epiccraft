package com.nekyoh.epiccraft.common.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.List;

/**
 * Chave inglesa: dano baixo. Por enquanto, clique direito num bloco gira ele
 * (escadas, pistões, observadores, funis, toras...). Base para as ferramentas futuras.
 */
public class WrenchItem extends Item {

    public WrenchItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        return tryRotate(context);
    }

    /** Gira o bloco clicado (usado também pela Inventor Wrench). */
    public static InteractionResult tryRotate(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Player player = context.getPlayer();
        BlockState state = level.getBlockState(pos);

        if (player == null || !player.mayUseItemAt(pos, context.getClickedFace(), context.getItemInHand())) {
            return InteractionResult.PASS;
        }
        // Blocos de duas partes quebrariam ao girar
        if (state.hasProperty(BlockStateProperties.BED_PART)
                || state.hasProperty(BlockStateProperties.DOUBLE_BLOCK_HALF)
                || (state.hasProperty(BlockStateProperties.CHEST_TYPE) && state.getValue(BlockStateProperties.CHEST_TYPE) != ChestType.SINGLE)
                || (state.hasProperty(BlockStateProperties.EXTENDED) && state.getValue(BlockStateProperties.EXTENDED))) {
            return InteractionResult.PASS;
        }

        Property<?> property = null;
        if (state.hasProperty(BlockStateProperties.FACING)) property = BlockStateProperties.FACING;
        else if (state.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) property = BlockStateProperties.HORIZONTAL_FACING;
        else if (state.hasProperty(BlockStateProperties.FACING_HOPPER)) property = BlockStateProperties.FACING_HOPPER;
        else if (state.hasProperty(BlockStateProperties.AXIS)) property = BlockStateProperties.AXIS;
        else if (state.hasProperty(BlockStateProperties.HORIZONTAL_AXIS)) property = BlockStateProperties.HORIZONTAL_AXIS;
        if (property == null) return InteractionResult.PASS;

        // Procura a próxima rotação válida
        BlockState rotated = state;
        for (int i = 0; i < 6; i++) {
            rotated = rotated.cycle(property);
            if (rotated == state) return InteractionResult.PASS;
            if (rotated.canSurvive(level, pos)) break;
        }
        if (!rotated.canSurvive(level, pos)) return InteractionResult.PASS;

        if (!level.isClientSide) {
            level.setBlock(pos, rotated, 3);
            level.playSound(null, pos, SoundEvents.ITEM_FRAME_ROTATE_ITEM, SoundSource.BLOCKS, 1.0F, 0.8F);
            context.getItemInHand().hurtAndBreak(1, player, LivingEntity.getSlotForHand(context.getHand()));
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(1, attacker, net.minecraft.world.entity.EquipmentSlot.MAINHAND);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        EpicSwordItem.addAbilityTooltip(this, tooltip);
    }
}
