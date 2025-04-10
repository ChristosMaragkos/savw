package com.savw.networking;

import com.savw.PlayerData;
import com.savw.SkyAboveVoiceWithin;
import com.savw.StateSaverAndLoader;
import com.savw.effect.SkyAboveVoiceWithinMobEffects;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;

import static com.savw.shout.Shouts.UNRELENTING_FORCE;

public final class SkyAboveVoiceWithinNetworking {

    public static void initialize() {

        PayloadTypeRegistry.playC2S().register(UseShoutC2SPayload.USE_SHOUT_PAYLOAD_TYPE, UseShoutC2SPayload.CODEC);
        PayloadTypeRegistry.playC2S().register(DemandShoutAndWordSyncPayload.DEMAND_SHOUT_AND_WORD_SYNC_TYPE, DemandShoutAndWordSyncPayload.CODEC);
        PayloadTypeRegistry.playC2S().register(RequestCooldownSyncC2SPayload.REQUEST_COOLDOWN_SYNC_PAYLOAD_TYPE, RequestCooldownSyncC2SPayload.CODEC);
        PayloadTypeRegistry.playC2S().register(ChangeShoutPayload.CHANGE_SHOUT_PAYLOAD_TYPE, ChangeShoutPayload.CODEC);

        PayloadTypeRegistry.playS2C().register(UnlockedWordsPayload.UNLOCK_WORD_PAYLOAD_TYPE, UnlockedWordsPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(SyncCooldownS2CPayload.COOLDOWN_SYNC_PAYLOAD_TYPE, SyncCooldownS2CPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(SendShoutAndWordSyncPayload.SEND_SHOUT_AND_WORD_SYNC_TYPE, SendShoutAndWordSyncPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(InitialSyncPayload.INITIAL_SYNC_PAYLOAD_TYPE, InitialSyncPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(WordWallRemovedPayload.WORD_WALL_REMOVED_PAYLOAD_TYPE, WordWallRemovedPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(WhirlwindSprintS2CPayload.WHIRLWIND_SPRINT_PAYLOAD_TYPE, WhirlwindSprintS2CPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(ShoutFailedS2CPayload.SHOUT_FAILED_PAYLOAD_TYPE, ShoutFailedS2CPayload.CODEC);

        SkyAboveVoiceWithin.LOGGER.info("Sky Above, Voice Within networking initialized!");

        ServerPlayConnectionEvents.JOIN.register(((handler, sender, server) -> {
            PlayerData playerState = StateSaverAndLoader.getPlayerState(handler.getPlayer());

            server.execute(() -> ServerPlayNetworking.send(handler.getPlayer(), new InitialSyncPayload(playerState.currentShout, playerState.unlockedWords, playerState.shoutCooldown)));
        }));

        ServerPlayNetworking.registerGlobalReceiver(RequestCooldownSyncC2SPayload.REQUEST_COOLDOWN_SYNC_PAYLOAD_TYPE, ((requestCooldownSyncC2SPayload, context) -> {
            ServerPlayer player = context.player();
            player.setInvulnerable(player.hasEffect(MobEffects.INVISIBILITY) && player.hasEffect(MobEffects.GLOWING) && player.hasEffect(MobEffects.WEAKNESS));
            PlayerData playerState = StateSaverAndLoader.getPlayerState(player);

            if (playerState.shoutCooldown > 0) {
                playerState.shoutCooldown--;
            }
            ServerPlayNetworking.send(player, new SyncCooldownS2CPayload(playerState.shoutCooldown));
        }));

        ServerPlayNetworking.registerGlobalReceiver(UseShoutC2SPayload.USE_SHOUT_PAYLOAD_TYPE, ((useShoutC2SPayload, context) -> {
            Entity entity = context.player().level().getEntity(useShoutC2SPayload.entityId());

            if (entity instanceof ServerPlayer serverPlayer) {
                PlayerData playerState = StateSaverAndLoader.getPlayerState(serverPlayer);

                if (playerState.currentShout != null) {
                    playerState.currentShout.useShout(serverPlayer, serverPlayer.level(), useShoutC2SPayload.wordsUsed());
                    // Set the cooldown to the cooldown of the last word used, converted to ticks.
                    // Also, send the cooldown to the client, and give the player the Shout Cooldown effect for visual feedback.
                    playerState.shoutCooldown = !serverPlayer.isCreative() ?
                            playerState.currentShout.getSpecificWord(useShoutC2SPayload.wordsUsed() - 1).getCooldown() * 20 : 20;
                    serverPlayer.addEffect(new MobEffectInstance(SkyAboveVoiceWithinMobEffects.SHOUT_COOLDOWN, !serverPlayer.isCreative() ? playerState.shoutCooldown : 20, 0, false, true));
                    ServerPlayNetworking.send(serverPlayer, new SyncCooldownS2CPayload(playerState.shoutCooldown));
                } else {
                    serverPlayer.displayClientMessage(Component.literal("No Shout selected!"), true);
                }
            }
        }));

        ServerPlayNetworking.registerGlobalReceiver(ChangeShoutPayload.CHANGE_SHOUT_PAYLOAD_TYPE, ((changeShoutPayload, context) -> {
            ServerPlayer player = context.player();
            PlayerData playerState = StateSaverAndLoader.getPlayerState(player);

            playerState.currentShout = changeShoutPayload.shoutToSwitchTo();
        }));

        ServerPlayNetworking.registerGlobalReceiver(DemandShoutAndWordSyncPayload.DEMAND_SHOUT_AND_WORD_SYNC_TYPE, ((demandShoutAndWordSyncPayload, context) -> {
            ServerPlayer player = context.player();
            PlayerData playerState = StateSaverAndLoader.getPlayerState(player);

            ServerPlayNetworking.send(player, new SendShoutAndWordSyncPayload(playerState.currentShout != null ? playerState.currentShout : UNRELENTING_FORCE, playerState.unlockedWords));
        }));
    }

}
