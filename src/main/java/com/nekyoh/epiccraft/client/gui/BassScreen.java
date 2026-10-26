package com.nekyoh.epiccraft.client.gui;

import com.nekyoh.epiccraft.network.PlayNotePayload;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;
import org.lwjgl.glfw.GLFW;

import java.util.LinkedHashMap;
import java.util.Map;

/** Teclado do baixo: botões ou teclas A S D F G H J K tocam as notas. */
public class BassScreen extends Screen {
    private final InteractionHand hand;
    private final Map<Integer, NoteData> notes = new LinkedHashMap<>();

    public BassScreen(InteractionHand hand) {
        super(Component.translatable("gui.epiccraft.bass_title"));
        this.hand = hand;

        notes.put(GLFW.GLFW_KEY_A, new NoteData(0.707107F, "C"));
        notes.put(GLFW.GLFW_KEY_S, new NoteData(0.793701F, "D"));
        notes.put(GLFW.GLFW_KEY_D, new NoteData(0.890899F, "E"));
        notes.put(GLFW.GLFW_KEY_F, new NoteData(0.943874F, "F"));
        notes.put(GLFW.GLFW_KEY_G, new NoteData(1.059463F, "G"));
        notes.put(GLFW.GLFW_KEY_H, new NoteData(1.189207F, "A"));
        notes.put(GLFW.GLFW_KEY_J, new NoteData(1.334840F, "B"));
        notes.put(GLFW.GLFW_KEY_K, new NoteData(1.414214F, "C'"));
    }

    @Override
    protected void init() {
        int xStart = this.width / 2 - 88;
        int y = this.height / 2;
        int i = 0;
        for (NoteData data : notes.values()) {
            this.addRenderableWidget(Button.builder(Component.literal(data.name), b -> playNote(data.pitch))
                    .bounds(xStart + (i * 22), y, 20, 20)
                    .build());
            i++;
        }
    }

    private void playNote(float pitch) {
        Player player = this.minecraft == null ? null : this.minecraft.player;
        if (player == null) return;
        // Toca na hora para quem está tocando; o servidor repassa para os outros e gasta durabilidade
        player.level().playLocalSound(player.getX(), player.getY(), player.getZ(),
                SoundEvents.NOTE_BLOCK_BASS.value(), SoundSource.PLAYERS, 1.0F, pitch, false);
        PacketDistributor.sendToServer(new PlayNotePayload(pitch, hand == InteractionHand.OFF_HAND));
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        // Na 1.21 o super.render já desenha o fundo (chamar renderBackground de novo trava o jogo)
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, this.height / 2 - 40, 0xFFFFFF);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        NoteData data = notes.get(keyCode);
        if (data != null) {
            playNote(data.pitch);
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private record NoteData(float pitch, String name) {}
}
