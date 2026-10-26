package com.nekyoh.epiccraft.common.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

/** Enderman Sword: clique direito teleporta para onde você está olhando (até 32 blocos). */
public class EndermanSwordItem extends EpicSwordItem {
    private static final double RANGE = 32.0D;
    private static final int COOLDOWN = 40;

    public EndermanSwordItem(Tier tier, Item.Properties properties) {
        super(tier, properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (level instanceof ServerLevel serverLevel) {
            Vec3 destination = findDestination(serverLevel, player);
            if (destination == null) {
                return InteractionResultHolder.fail(stack);
            }

            Vec3 from = player.position();
            spawnPortalParticles(serverLevel, from, player);
            level.playSound(null, from.x, from.y, from.z, SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1.0F, 1.0F);

            player.stopRiding();
            player.teleportTo(destination.x, destination.y, destination.z);
            player.resetFallDistance();

            spawnPortalParticles(serverLevel, destination, player);
            level.playSound(null, destination.x, destination.y, destination.z, SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1.0F, 1.2F);

            stack.hurtAndBreak(2, player, LivingEntity.getSlotForHand(hand));
        }
        player.getCooldowns().addCooldown(this, COOLDOWN);
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    /** Procura um lugar seguro no final do raio de visão. */
    private static Vec3 findDestination(ServerLevel level, Player player) {
        Vec3 eye = player.getEyePosition();
        Vec3 end = eye.add(player.getLookAngle().scale(RANGE));
        BlockHitResult hit = level.clip(new ClipContext(eye, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));

        BlockPos base;
        if (hit.getType() == HitResult.Type.BLOCK) {
            base = hit.getBlockPos().relative(hit.getDirection());
        } else {
            base = BlockPos.containing(end);
        }

        // Tenta a posição encontrada e alguns blocos acima/abaixo
        for (int dy : new int[]{0, 1, -1, 2}) {
            BlockPos pos = base.above(dy);
            Vec3 candidate = new Vec3(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);
            Vec3 delta = candidate.subtract(player.position());
            if (level.noCollision(player, player.getBoundingBox().move(delta)) && level.isInWorldBounds(pos)) {
                return candidate;
            }
        }
        return null;
    }

    private static void spawnPortalParticles(ServerLevel level, Vec3 pos, Player player) {
        level.sendParticles(ParticleTypes.PORTAL, pos.x, pos.y + player.getBbHeight() * 0.5D, pos.z,
                40, 0.4D, 0.8D, 0.4D, 0.3D);
    }
}
