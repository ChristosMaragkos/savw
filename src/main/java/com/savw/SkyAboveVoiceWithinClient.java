package com.savw;

import com.mojang.blaze3d.platform.InputConstants;
import com.savw.entity.projectile.fire.FireShockwaveRenderer;
import com.savw.entity.projectile.force.ForceShockwaveModel;
import com.savw.entity.projectile.force.ForceShockwaveRenderer;
import com.savw.entity.projectile.frost.FrostShockwaveRenderer;
import com.savw.networking.*;
import com.savw.shout.AbstractShout;
import com.savw.sound.SkyAboveVoiceWithinSounds;
import com.savw.sound.UnlockWordMovingSoundInstance;
import com.savw.ui.ShoutSelectionScreenPageOne;
import com.savw.word.ShoutWord;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.glfw.GLFW;

import java.util.HashSet;
import java.util.List;

import static com.savw.SkyAboveVoiceWithin.*;
import static com.savw.shout.Shouts.*;
import static com.savw.word.Words.ALL_WORDS;

public class SkyAboveVoiceWithinClient implements ClientModInitializer {

    public static PlayerData clientPlayerData = new PlayerData();

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
            clientPlayerData.unlockedWords = fixedWords;
            SkyAboveVoiceWithin.LOGGER.info("Player {} unlocked the word: {}", player.getName().getString(), payload.unlockedWords().getLast().getName());
        }
    }

    private static void handleInitialSync(InitialSyncPayload initialSyncPayload, ClientPlayNetworking.Context context) {
        clientPlayerData.currentShout = initialSyncPayload.currentShout();
        clientPlayerData.unlockedWords = initialSyncPayload.unlockedWords();
        clientPlayerData.shoutCooldown = initialSyncPayload.shoutCooldown();
    }

    private static void handleOnDemandSync(SendShoutAndWordSyncPayload payload, ClientPlayNetworking.Context context) {
        LocalPlayer player = context.client().player;
        assert player != null;

        clientPlayerData.currentShout = payload.currentShout();
        clientPlayerData.unlockedWords = payload.unlockedWords().stream()
                .map(word -> ALL_WORDS.stream()
                        .filter(w -> w.getName().equals(word.getName()))
                        .findFirst()
                        .orElseThrow(() -> new IllegalStateException("Unknown ShoutWord: " + word.getName())))
                .toList();
    }

    private static void handleTickCooldownSync(SyncCooldownS2CPayload payload, ClientPlayNetworking.Context context) {
        LocalPlayer player = context.client().player;
        assert player != null;

        clientPlayerData.shoutCooldown = payload.shoutCooldown();

    }

    /// # Yes, I'm aware this is a shitty way to handle this.
    /// <small>I really, REALLY don't care, though.</small>
    private static void handleWordWallRemoved(WordWallRemovedPayload payload, ClientPlayNetworking.Context context) {
        LocalPlayer player = context.client().player;
        assert player != null;

        context.client().getSoundManager().play(new UnlockWordMovingSoundInstance(
                player,
                getRandomWordUnlockSound(player.clientLevel),
                SoundSource.AMBIENT,
                player.clientLevel.random
        ));
    }

    private static void handleWhirlwindSprint(WhirlwindSprintS2CPayload payload, ClientPlayNetworking.Context context) {
        LocalPlayer player = context.client().player;
        assert player != null;
        Vec3 lookingDirection = player.getLookAngle().subtract(0, player.getLookAngle().y, 0);
        double thrust = 5 + (2.5 * (payload.wordsUsed() - 1));
        player.setSprinting(true);
        player.setDeltaMovement(lookingDirection.scale(thrust));
        player.setOnGround(true);
    }

    @SuppressWarnings("DataFlowIssue")
    private static void handleShoutFailed(ShoutFailedS2CPayload payload, ClientPlayNetworking.Context context) {
        LocalPlayer player = context.client().player;
        assert player != null;

        context.client().getSoundManager().play(new SimpleSoundInstance(SoundEvents.NOTE_BLOCK_BASS.value(),
                SoundSource.MASTER, 1f, 0.8f, context.client().level.getRandom(), context.client().player.blockPosition()));
        if (payload.fromCooldown()) {
            context.client().player.displayClientMessage(Component.literal("Your Shout is on cooldown!"), true);
        } else {
            context.client().player.displayClientMessage(Component.literal("You have not unlocked any words for this Shout!"), true);
        }
    }

    private static SoundEvent getRandomWordUnlockSound(ClientLevel level) {
        assert level != null;
        return switch (level.random.nextInt(3)) {
            case 0 -> SkyAboveVoiceWithinSounds.WORD_UNLOCKED_1;
            case 1 -> SkyAboveVoiceWithinSounds.WORD_UNLOCKED_2;
            case 2 -> SkyAboveVoiceWithinSounds.WORD_UNLOCKED_3;
            default -> throw new IllegalStateException("Unexpected value!");
        };
    }

    public static final ModelLayerLocation SHOCKWAVE_LAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(SkyAboveVoiceWithin.MOD_ID, "shockwave"),
            "main"
    );

    @Override
    public void onInitializeClient() {

        EntityModelLayerRegistry.registerModelLayer(SHOCKWAVE_LAYER, ForceShockwaveModel::createBodyLayer);

        EntityRendererRegistry.register(FORCE_SHOCKWAVE, ForceShockwaveRenderer::new);
        EntityRendererRegistry.register(FIRE_SHOCKWAVE, FireShockwaveRenderer::new);
        EntityRendererRegistry.register(FROST_SHOCKWAVE, FrostShockwaveRenderer::new);

        @SuppressWarnings("NoTranslation") KeyMapping shoutKeybind = KeyBindingHelper.registerKeyBinding(
                new KeyMapping(
                        "Use Shout",
                        InputConstants.Type.KEYSYM,
                        GLFW.GLFW_KEY_Z,
                        "Sky Above, Voice Within Keybindings"
                )
        );

        @SuppressWarnings("NoTranslation") KeyMapping toggleShoutMenuKeybind = KeyBindingHelper.registerKeyBinding(
                new KeyMapping(
                        "Open Shout Menu",
                        InputConstants.Type.KEYSYM,
                        GLFW.GLFW_KEY_V,
                        "Sky Above, Voice Within Keybindings"
                )
        );

        final boolean[] isShouting = {false};
        final int[] shoutActionTick = {0};
        final int[] wordsQueued = {0};

        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            if (client.player != null) {


                ClientPlayNetworking.send(new RequestCooldownSyncC2SPayload(client.player.getUUID()));

                if (shoutKeybind.consumeClick()) {
                    ClientPlayNetworking.send(new DemandShoutAndWordSyncPayload(client.player.getUUID()));
                }

                if (toggleShoutMenuKeybind.consumeClick()) {
                    ClientPlayNetworking.send(new DemandShoutAndWordSyncPayload(client.player.getUUID()));
                    client.setScreen(new ShoutSelectionScreenPageOne());
                }

                // --- SHOUT MECHANICS --- \\
                if (client.level == null) {
                    return;
                }

                if (shoutKeybind.isDown() && clientPlayerData.currentShout != DUMMY_INITIAL_SHOUT &&
                        clientPlayerData.currentShout.getUnlockedWordsCount(clientPlayerData.unlockedWords) != 0
                && clientPlayerData.shoutCooldown <= 0) {


                    if (!isShouting[0]) {
                        // Just started holding
                        shoutActionTick[0] = 0;
                        wordsQueued[0] = 0;
                        isShouting[0] = true;
                    }

                    shoutActionTick[0]++; // Increment while holding

                    AbstractShout currentShout = clientPlayerData.currentShout;
                    int unlockedWordsForShout = currentShout.getUnlockedWordsCount(clientPlayerData.unlockedWords);

                    if (unlockedWordsForShout >= 1 && shoutActionTick[0] == 1) {
                        client.player.displayClientMessage(Component.literal(currentShout.getFirstWord().getName() + "..."), true);
                        wordsQueued[0] = 1;
                    }
                    if (unlockedWordsForShout >= 2 && shoutActionTick[0] == 15) {
                        client.player.displayClientMessage(Component.literal(currentShout.getFirstWord().getName() + "... " + currentShout.getSecondWord().getName() + "..."), true);
                        wordsQueued[0] = 2;
                    }
                    if (unlockedWordsForShout >= 3 && shoutActionTick[0] == 30) {
                        client.player.displayClientMessage(Component.literal(currentShout.getFirstWord().getName() + " " + currentShout.getSecondWord().getName() + " " + currentShout.getThirdWord().getName() + "!"), true);
                        wordsQueued[0] = 3;
                    }

                    // If the queued words match the unlocked words, trigger the shout immediately
                    if (wordsQueued[0] == unlockedWordsForShout) {
                        isShouting[0] = false;
                        shoutActionTick[0] = 0;

                        switch (unlockedWordsForShout) {
                            case 1 -> client.player.displayClientMessage(Component.literal(currentShout.getFirstWord().getName() + "!"), true);

                            case 2 -> client.player.displayClientMessage(Component.literal(currentShout.getFirstWord().getName() + " "
                                    + currentShout.getSecondWord().getName() + "!"), true);

                            case 3 -> client.player.displayClientMessage(Component.literal(currentShout.getFirstWord().getName() + " "
                                    + currentShout.getSecondWord().getName() + " " + currentShout.getThirdWord().getName() + "!"), true);
                        }
                        ClientPlayNetworking.send(new UseShoutC2SPayload(client.player.getId(), wordsQueued[0]));
                        wordsQueued[0] = 0; // Reset so next shout works correctly
                    }

                } else if (isShouting[0]) {
                    // Key was released, finalize the shout
                    isShouting[0] = false;
                    shoutActionTick[0] = 0;

                    // Prevents multiple triggers during the same key press
                    int wordsUsed = wordsQueued[0];
                    wordsQueued[0] = 0;

                    if (wordsUsed > 0) {
                        switch (wordsUsed) {
                            case 1 ->
                                    client.player.displayClientMessage(Component.literal(clientPlayerData.currentShout.getFirstWord().getName() + "!"), true);

                            case 2 ->
                                    client.player.displayClientMessage(Component.literal(clientPlayerData.currentShout.getFirstWord().getName() + " "
                                            + clientPlayerData.currentShout.getSecondWord().getName() + "!"), true);

                            case 3 ->
                                    client.player.displayClientMessage(Component.literal(clientPlayerData.currentShout.getFirstWord().getName() + " "
                                            + clientPlayerData.currentShout.getSecondWord().getName() + " "
                                            + clientPlayerData.currentShout.getThirdWord().getName() + "!"), true);
                        }
                        ClientPlayNetworking.send(new UseShoutC2SPayload(client.player.getId(), wordsUsed));
                    }
                }
            }
        });

        ClientPlayNetworking.registerGlobalReceiver(UnlockedWordsPayload.UNLOCK_WORD_PAYLOAD_TYPE, SkyAboveVoiceWithinClient::handleUnlockedWordsPayload);

        ClientPlayNetworking.registerGlobalReceiver(InitialSyncPayload.INITIAL_SYNC_PAYLOAD_TYPE, SkyAboveVoiceWithinClient::handleInitialSync);

        ClientPlayNetworking.registerGlobalReceiver(SendShoutAndWordSyncPayload.SEND_SHOUT_AND_WORD_SYNC_TYPE, SkyAboveVoiceWithinClient::handleOnDemandSync);

        ClientPlayNetworking.registerGlobalReceiver(SyncCooldownS2CPayload.COOLDOWN_SYNC_PAYLOAD_TYPE, SkyAboveVoiceWithinClient::handleTickCooldownSync);

        ClientPlayNetworking.registerGlobalReceiver(WordWallRemovedPayload.WORD_WALL_REMOVED_PAYLOAD_TYPE, SkyAboveVoiceWithinClient::handleWordWallRemoved);

        ClientPlayNetworking.registerGlobalReceiver(WhirlwindSprintS2CPayload.WHIRLWIND_SPRINT_PAYLOAD_TYPE, SkyAboveVoiceWithinClient::handleWhirlwindSprint);

        ClientPlayNetworking.registerGlobalReceiver(ShoutFailedS2CPayload.SHOUT_FAILED_PAYLOAD_TYPE, SkyAboveVoiceWithinClient::handleShoutFailed);

    }

}
