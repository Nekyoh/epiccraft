package com.nekyoh.epiccraft.network;

import com.nekyoh.epiccraft.EpicCraft;
import com.nekyoh.epiccraft.common.item.custom.BassItem;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/**
 * Cliente -> servidor: o jogador tocou uma nota no baixo.
 * O servidor toca o som para os outros jogadores ouvirem e gasta durabilidade
 * (antes a durabilidade era gasta só no cliente e não salvava).
 */
public record PlayNotePayload(float pitch, boolean offhand) implements CustomPacketPayload {
    public static final Type<PlayNotePayload> TYPE = new Type<>(EpicCraft.id("play_note"));

    public static final StreamCodec<ByteBuf, PlayNotePayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.FLOAT, PlayNotePayload::pitch,
            ByteBufCodecs.BOOL, PlayNotePayload::offhand,
            PlayNotePayload::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(PlayNotePayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            InteractionHand hand = payload.offhand() ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
            ItemStack stack = player.getItemInHand(hand);
            if (!(stack.getItem() instanceof BassItem)) return;

            float pitch = Mth.clamp(payload.pitch(), 0.5F, 2.0F);
            // "player" como primeiro argumento = não manda de volta para quem tocou (ele já ouviu localmente)
            player.level().playSound(player, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.NOTE_BLOCK_BASS.value(), SoundSource.PLAYERS, 1.0F, pitch);
            stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
        });
    }
}
