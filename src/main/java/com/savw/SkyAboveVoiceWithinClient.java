package com.savw;

import com.mojang.blaze3d.platform.InputConstants;
import com.savw.entity.projectile.ForceShockwaveRenderer;
import com.savw.networking.UseShoutC2SPayload;
import com.savw.networking.UnlockedWordsPayload;
import com.savw.word.ShoutWord;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;

import org.lwjgl.glfw.GLFW;

import java.util.HashSet;
import java.util.List;

import static com.savw.SkyAboveVoiceWithin.FORCE_SHOCKWAVE;
import static com.savw.word.Words.ALL_WORDS;

public class SkyAboveVoiceWithinClient implements ClientModInitializer {

    public static boolean isToggled = false;

    private static void handleUnlockedWordsPayload(UnlockedWordsPayload payload, ClientPlayNetworking.Context context) {
        LocalPlayer player = context.client().player;
        assert player != null;

        List<ShoutWord> fixedWords = payload.unlockedWords().stream()
                .map(word -> ALL_WORDS.stream()
                        .filter(w -> w.getName().equals(word.getName()))
                        .findFirst()
                        .orElseThrow(() -> new IllegalStateException("Unknown ShoutWord: " + word.getName())))
                .toList();
        if (new HashSet<>(fixedWords).containsAll(ALL_WORDS)) {
            player.displayClientMessage(Component.literal("You have unlocked all words!"), true);
        } else {
            SkyAboveVoiceWithin.LOGGER.info("Player {} unlocked the word: {}", player.getName().getString(), payload.unlockedWords().getLast().getName());
        }
    }

    @Override
    public void onInitializeClient() {
        KeyMapping shoutKeybind = KeyBindingHelper.registerKeyBinding(
                new KeyMapping(
                        "Use Shout",
                        InputConstants.Type.KEYSYM,
                        GLFW.GLFW_KEY_Z,
                        "Sky Above, Voice Within Keybindings"
                )
        );

        KeyMapping toggleShoutMenuKeybind = KeyBindingHelper.registerKeyBinding(
                new KeyMapping(
                        "Open Shout Menu",
                        InputConstants.Type.KEYSYM,
                        GLFW.GLFW_KEY_V,
                        "Sky Above, Voice Within Keybindings"
                )
        );

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (shoutKeybind.consumeClick()) {
                assert client.player != null;
                client.player.displayClientMessage(Component.literal("Shout used!"), true);
                UseShoutC2SPayload payload = new UseShoutC2SPayload(client.player.getId());
                ClientPlayNetworking.send(payload);
            }
            if (toggleShoutMenuKeybind.consumeClick()) {
                assert client.player != null;
                isToggled = !isToggled;
                if (isToggled) {
                    client.player.displayClientMessage(Component.literal("Shout Menu Opened!"), true);
                } else {
                    client.player.displayClientMessage(Component.literal("Shout Menu Closed!"), true);
                }
            }

        });

        ClientPlayNetworking.registerGlobalReceiver(UnlockedWordsPayload.UNLOCK_WORD_PAYLOAD_TYPE, SkyAboveVoiceWithinClient::handleUnlockedWordsPayload);

        EntityRendererRegistry.register(FORCE_SHOCKWAVE, ForceShockwaveRenderer::new);

    }
}
