package com.nekyoh.epiccraft.common.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;

/** Pé de cabra: abre portas/alçapões de ferro e arranca grades. */
public class CrowbarItem extends Item {

    public CrowbarItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Player player = context.getPlayer();
        ItemStack stack = context.getItemInHand();

        if (player == null) return InteractionResult.PASS;
        if (!player.mayUseItemAt(pos, context.getClickedFace(), stack)) return InteractionResult.FAIL;

        BlockState state = level.getBlockState(pos);
        EquipmentSlot slot = LivingEntity.getSlotForHand(context.getHand());

        // Porta de ferro
        if (state.is(Blocks.IRON_DOOR)) {
            if (!level.isClientSide) {
                BlockPos lowerPos = state.getValue(DoorBlock.HALF) == DoubleBlockHalf.LOWER ? pos : pos.below();
                BlockPos upperPos = lowerPos.above();
                BlockState lower = level.getBlockState(lowerPos);
                BlockState upper = level.getBlockState(upperPos);
                if (lower.is(Blocks.IRON_DOOR) && upper.is(Blocks.IRON_DOOR)) {
                    boolean isOpen = lower.getValue(DoorBlock.OPEN);
                    level.setBlock(lowerPos, lower.setValue(DoorBlock.OPEN, !isOpen), 10);
                    level.setBlock(upperPos, upper.setValue(DoorBlock.OPEN, !isOpen), 10);
                    level.playSound(null, pos, !isOpen ? SoundEvents.IRON_DOOR_OPEN : SoundEvents.IRON_DOOR_CLOSE,
                            SoundSource.BLOCKS, 1.0F, 1.0F);
                    stack.hurtAndBreak(1, player, slot);
                }
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        // Alçapão de ferro
        if (state.is(Blocks.IRON_TRAPDOOR)) {
            if (!level.isClientSide) {
                boolean isOpen = state.getValue(BlockStateProperties.OPEN);
                level.setBlock(pos, state.setValue(BlockStateProperties.OPEN, !isOpen), 10);
                level.playSound(null, pos, !isOpen ? SoundEvents.IRON_TRAPDOOR_OPEN : SoundEvents.IRON_TRAPDOOR_CLOSE,
                        SoundSource.BLOCKS, 1.0F, 1.0F);
                stack.hurtAndBreak(1, player, slot);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        // Grades de ferro
        if (state.getBlock() instanceof IronBarsBlock) {
            if (!level.isClientSide) {
                level.destroyBlock(pos, true, player);
                level.playSound(null, pos, SoundEvents.IRON_GOLEM_REPAIR, SoundSource.BLOCKS, 1.0F, 0.8F);
                stack.hurtAndBreak(2, player, slot);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        return InteractionResult.PASS;
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return true;
    }

    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(1, attacker, EquipmentSlot.MAINHAND);
    }
}
