package com.savw;


import com.savw.block.ModBlocks;
import com.savw.block.blocks.ModBlockEntityTypes;
import com.savw.effect.ModMobEffects;
import com.savw.entity.projectile.FireShockwave;
import com.savw.entity.projectile.ForceShockwave;
import com.savw.entity.projectile.FrostShockwave;
import com.savw.networking.*;
import com.savw.shout.Shouts;
import com.savw.sound.SkyAboveVoiceWithinSounds;
import com.savw.word.Words;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.savw.shout.Shouts.UNRELENTING_FORCE;


public class SkyAboveVoiceWithin implements ModInitializer {
	public static final String MOD_ID = "savw";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final EntityType<ForceShockwave> FORCE_SHOCKWAVE = Registry.register(
			BuiltInRegistries.ENTITY_TYPE,
			ResourceLocation.fromNamespaceAndPath(MOD_ID, "force_shockwave"),
			EntityType.Builder.<ForceShockwave>of(ForceShockwave::new, MobCategory.MISC)
					.sized(1.0f, 1.0f)
					.updateInterval(10)
					.alwaysUpdateVelocity(true)
					.build(ResourceKey.create(
							Registries.ENTITY_TYPE,
							ResourceLocation.fromNamespaceAndPath(MOD_ID, "force_shockwave")
					))
	);

	public static final EntityType<FireShockwave> FIRE_SHOCKWAVE = Registry.register(
			BuiltInRegistries.ENTITY_TYPE,
			ResourceLocation.fromNamespaceAndPath(MOD_ID, "fire_shockwave"),
			EntityType.Builder.<FireShockwave>of(FireShockwave::new, MobCategory.MISC)
					.sized(1.0f, 1.0f)
					.updateInterval(10)
					.alwaysUpdateVelocity(true)
					.build(ResourceKey.create(
							Registries.ENTITY_TYPE,
							ResourceLocation.fromNamespaceAndPath(MOD_ID, "fire_shockwave")
					))
	);

	public static final EntityType<FrostShockwave> FROST_SHOCKWAVE = Registry.register(
			BuiltInRegistries.ENTITY_TYPE,
			ResourceLocation.fromNamespaceAndPath(MOD_ID, "frost_shockwave"),
			EntityType.Builder.<FrostShockwave>of(FrostShockwave::new, MobCategory.MISC)
					.sized(1.0f, 1.0f)
					.updateInterval(10)
					.alwaysUpdateVelocity(true)
					.build(ResourceKey.create(
							Registries.ENTITY_TYPE,
							ResourceLocation.fromNamespaceAndPath(MOD_ID, "frost_shockwave")
					))
	);

    @Override
	public void onInitialize() {
		LOGGER.info("Sky Above, Voice Within mod initialized!");

		PayloadTypeRegistry.playC2S().register(UseShoutC2SPayload.USE_SHOUT_PAYLOAD_TYPE, UseShoutC2SPayload.CODEC);
		PayloadTypeRegistry.playC2S().register(DemandShoutAndWordSyncPayload.DEMAND_SHOUT_AND_WORD_SYNC_TYPE, DemandShoutAndWordSyncPayload.CODEC);
		PayloadTypeRegistry.playC2S().register(RequestCooldownSyncC2SPayload.REQUEST_COOLDOWN_SYNC_PAYLOAD_TYPE, RequestCooldownSyncC2SPayload.CODEC);
		PayloadTypeRegistry.playC2S().register(ChangeShoutPayload.CHANGE_SHOUT_PAYLOAD_TYPE, ChangeShoutPayload.CODEC);

		PayloadTypeRegistry.playS2C().register(UnlockedWordsPayload.UNLOCK_WORD_PAYLOAD_TYPE, UnlockedWordsPayload.CODEC);
		PayloadTypeRegistry.playS2C().register(SyncCooldownS2CPayload.COOLDOWN_SYNC_PAYLOAD_TYPE, SyncCooldownS2CPayload.CODEC);
		PayloadTypeRegistry.playS2C().register(SendShoutAndWordSyncPayload.SEND_SHOUT_AND_WORD_SYNC_TYPE, SendShoutAndWordSyncPayload.CODEC);
		PayloadTypeRegistry.playS2C().register(InitialSyncPayload.INITIAL_SYNC_PAYLOAD_TYPE, InitialSyncPayload.CODEC);
		PayloadTypeRegistry.playS2C().register(WordWallRemovedPayload.WORD_WALL_REMOVED_PAYLOAD_TYPE, WordWallRemovedPayload.CODEC);

        ModBlocks.initialize();
		ModMobEffects.initialize();
		ModBlockEntityTypes.initialize();
		Words.initialize();
		Shouts.initialize();
		SkyAboveVoiceWithinSounds.initialize();

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
				LOGGER.warn("Using shout: {}", playerState.currentShout != null ? playerState.currentShout.getName() : "null");

                if (playerState.currentShout != null) {
                    playerState.currentShout.useShout(serverPlayer, serverPlayer.level(), useShoutC2SPayload.wordsUsed());
					playerState.shoutCooldown = playerState.currentShout.getSpecificWord(useShoutC2SPayload.wordsUsed() - 1).getCooldown() * 20;
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