package com.savw;


import com.savw.block.ModBlocks;
import com.savw.block.blocks.ModBlockEntityTypes;
import com.savw.entity.projectile.ForceShockwave;
import com.savw.networking.UseShoutC2SPayload;
import com.savw.networking.UnlockedWordsPayload;
import com.savw.shout.Shouts;
import com.savw.word.Words;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SkyAboveVoiceWithin implements ModInitializer {
	public static final String MOD_ID = "savw";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final ResourceLocation UNLOCKED_WORDS = ResourceLocation.fromNamespaceAndPath(MOD_ID, "unlocked_words");

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

    @Override
	public void onInitialize() {
		LOGGER.info("Sky Above, Voice Within mod initialized!");

		PayloadTypeRegistry.playC2S().register(UseShoutC2SPayload.USE_SHOUT_PAYLOAD_TYPE, UseShoutC2SPayload.CODEC);

		PayloadTypeRegistry.playS2C().register(UnlockedWordsPayload.UNLOCK_WORD_PAYLOAD_TYPE, UnlockedWordsPayload.CODEC);

//		PlayerBlockBreakEvents.AFTER.register(((level, player, blockPos, blockState, blockEntity) -> {
//			MinecraftServer server = level.getServer();
//
//			assert server != null;
//
//            PlayerData playerState = StateSaverAndLoader.getPlayerState(player);
//
//			if (!new HashSet<>(playerState.unlockedWords).containsAll(ALL_WORDS)) {
//				ShoutWord wordToUnlock = null;
//
//				while (wordToUnlock == null || playerState.unlockedWords.contains(wordToUnlock)) {
//					// Get a random shoutToSwitchTo and try to unlock a word from it
//					// If the word is already unlocked, try again
//					// If all words are unlocked, break the loop
//					AbstractShout shout = Shouts.getRandomShout(level);
//					wordToUnlock = shout.tryUnlockWord(playerState.unlockedWords);
//				}
//				playerState.unlockedWords.add(wordToUnlock);
//			}
//
//			ServerPlayer serverPlayer = server.getPlayerList().getPlayer(player.getUUID());
//			server.execute(() -> {
//				assert serverPlayer != null;
//				ServerPlayNetworking.send(serverPlayer, new UnlockedWordsPayload(playerState.unlockedWords));
//			});
//
//		}));

		ModBlocks.initialize();
		ModBlockEntityTypes.initialize();
		Words.initialize();
		Shouts.initialize();

		ServerPlayNetworking.registerGlobalReceiver(UseShoutC2SPayload.USE_SHOUT_PAYLOAD_TYPE, ((becomeEtherealC2SPayload, context) -> {
			Entity entity = context.player().level().getEntity(becomeEtherealC2SPayload.entityId());

			if (entity instanceof ServerPlayer serverPlayer) {
				PlayerData playerState = StateSaverAndLoader.getPlayerState(serverPlayer);

                if (playerState.currentShout != null) {
                    playerState.currentShout.useShout(serverPlayer, serverPlayer.level());
                } else {
					serverPlayer.displayClientMessage(Component.literal("No Shout selected!"), true);
				}
            }
		}));

	}
}