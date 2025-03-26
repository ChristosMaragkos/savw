package com.savw.block.blocks;

import com.savw.PlayerData;
import com.savw.StateSaverAndLoader;
import com.savw.networking.UnlockedWordsPayload;
import com.savw.shout.AbstractShout;
import com.savw.shout.Shouts;
import com.savw.word.ShoutWord;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitlesAnimationPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashSet;
import java.util.Objects;

import static com.savw.block.blocks.ModBlockEntityTypes.WORD_WALL_BLOCK_ENTITY_TYPE;
import static com.savw.word.Words.ALL_WORDS;

public class WordWallBlockEntity extends BlockEntity implements TickableBlockEntity{

    public WordWallBlockEntity(BlockPos pos, BlockState blockState) {
        super(WORD_WALL_BLOCK_ENTITY_TYPE, pos, blockState);
    }

    int ticksUntilUnlock = 100;

    @Override
    public void tick() {
        //SkyAboveVoiceWithin.LOGGER.info("Buzzinga! Word Wall Block Entity ticked at {}", getBlockPos().toShortString());
        assert level != null;
        Player closestPlayer = level.getNearestPlayer(getBlockPos().getX(),
                getBlockPos().getY(),
                getBlockPos().getZ(),
                5, false);

        if (getLevel() instanceof ServerLevel serverLevel && closestPlayer != null) {
            double dx = closestPlayer.getX() - (getBlockPos().getX() + 0.5);
            double dy = closestPlayer.getY() - (getBlockPos().getY() + 0.5);
            double dz = closestPlayer.getZ() - (getBlockPos().getZ() + 0.5);
            double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);
            double velocityX = dx / distance * 0.1;
            double velocityY = dy / distance * 0.1;
            double velocityZ = dz / distance * 0.1;

            serverLevel.sendParticles(ParticleTypes.TRIAL_SPAWNER_DETECTED_PLAYER_OMINOUS,
                    getBlockPos().getX() + 0.5,
                    getBlockPos().getY() + 0.5,
                    getBlockPos().getZ() + 0.5,
                    4, velocityX, velocityY, velocityZ, 0.1);
        }

        //SkyAboveVoiceWithin.LOGGER.info("Closest Player detected: {}", closestPlayer != null ? closestPlayer.getName().getString() : "null");

        if (closestPlayer instanceof ServerPlayer serverPlayer && !Objects.requireNonNull(getLevel()).isClientSide()) {
            serverPlayer.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 30, 1, false, false));
            if (this.ticksUntilUnlock > 0) {
                this.ticksUntilUnlock--;
                if (this.ticksUntilUnlock == 0) {

                    getLevel().playSound(null, getBlockPos().getX(),
                            getBlockPos().getY(),
                            getBlockPos().getZ(),
                            SoundEvents.ZOMBIE_VILLAGER_CURE,
                            closestPlayer.getSoundSource(),
                            1.0f, 1.0f);

                    serverPlayer.removeEffect(MobEffects.BLINDNESS);

                    PlayerData playerState = StateSaverAndLoader.getPlayerState(serverPlayer);

                    if (!new HashSet<>(playerState.unlockedWords).containsAll(ALL_WORDS)) {
                        ShoutWord wordToUnlock = null;
                        AbstractShout shoutOfWord = null;
                        while (wordToUnlock == null || playerState.unlockedWords.contains(wordToUnlock)) {
                            // Get a random shoutToSwitchTo and try to unlock a word from it
                            // If the word is already unlocked, try again
                            // If all words are unlocked, break the loop
                            shoutOfWord = Shouts.getRandomShout(Objects.requireNonNull(level));
                            wordToUnlock = shoutOfWord.tryUnlockWord(playerState.unlockedWords);
                        }
                        playerState.unlockedWords.add(wordToUnlock);
                        serverPlayer.connection.send(new ClientboundSetTitlesAnimationPacket(20, 40, 20));
                        serverPlayer.connection.send(new ClientboundSetTitleTextPacket(Component.literal("WORD OF POWER LEARNED")));
                        serverPlayer.connection.send(new ClientboundSetSubtitleTextPacket(Component.literal(wordToUnlock.getName() + ", " + shoutOfWord.getName())));
                    }

                    ServerPlayNetworking.send(serverPlayer, new UnlockedWordsPayload(StateSaverAndLoader.getPlayerState(serverPlayer).unlockedWords));
                    this.setRemoved();
                }
            }
        } else {
            this.ticksUntilUnlock = 100;
        }

    }

}
