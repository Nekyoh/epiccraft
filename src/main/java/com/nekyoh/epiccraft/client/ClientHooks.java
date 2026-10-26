package com.nekyoh.epiccraft.client;

import com.nekyoh.epiccraft.client.gui.BassScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;

/**
 * Código que só existe no cliente. Os itens chamam estes métodos apenas quando
 * level.isClientSide é true, então o servidor dedicado nunca carrega esta classe.
 */
public final class ClientHooks {

    public static void openBassScreen(InteractionHand hand) {
        Minecraft.getInstance().setScreen(new BassScreen(hand));
    }

    private ClientHooks() {}
}
