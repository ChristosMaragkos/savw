package com.savw.block.blocks;

import com.savw.PlayerData;
import com.savw.StateSaverAndLoader;
import com.savw.networking.UnlockedWordsPayload;
import com.savw.networking.WordWallRemovedPayload;
import com.savw.shout.AbstractShout;
import com.savw.shout.Shouts;
import com.savw.sound.SkyAboveVoiceWithinSounds;
import com.savw.sound.WordWallLoopingSoundInstance;
import com.savw.word.ShoutWord;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitlesAnimationPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;

import static com.savw.block.SkyAboveVoiceWithinBlocks.*;
import static com.savw.block.blocks.SkyAboveVoiceWithinBlockEntityTypes.WORD_WALL_BLOCK_ENTITY_TYPE;
import static com.savw.word.Words.ALL_WORDS;

public class WordWallBlockEntity extends BlockEntity implements TickableBlockEntity{

    public WordWallBlockEntity(BlockPos pos, BlockState blockState) {
        super(WORD_WALL_BLOCK_ENTITY_TYPE, pos, blockState);
    }

    private static final HashMap<Block, Block> SMART_TO_DUMB_BLOCK_MAP = new HashMap<>(Map.of(
            SMART_ETCHED_DEEPSLATE, DUMB_ETCHED_DEEPSLATE,
            SMART_ETCHED_BLACKSTONE, DUMB_ETCHED_BLACKSTONE,
            SMART_ETCHED_END_STONE, DUMB_ETCHED_END_STONE
    ));

    private final int INITIAL_UNLOCK_TICKS = 140;
    private int ticksUntilUnlock = INITIAL_UNLOCK_TICKS;
    @Nullable public Player closestPlayer;

    @Override
    public void tick() {
        assert level != null;

        closestPlayer = level.getNearestPlayer(getBlockPos().getX(),
                getBlockPos().getY(),
                getBlockPos().getZ(),
                5, false);

        if (level.isClientSide()) {
            if (tryPlaySound(level, this)) return;
        }

        trySpawnParticles(closestPlayer);

        if (closestPlayer instanceof ServerPlayer serverPlayer && !Objects.requireNonNull(getLevel()).isClientSide() && hasClearLineOfSight(serverPlayer)) {
            wordUnlockTick(serverPlayer);
        } else {
            this.ticksUntilUnlock = INITIAL_UNLOCK_TICKS;
        }

    }

    @Environment(EnvType.CLIENT)
    public boolean tryPlaySound(Level level, WordWallBlockEntity wordWallBlockEntity) {
        Minecraft client = Minecraft.getInstance();
        assert client.level != null;
        if (client.getSoundManager().isActive(wordWallLoopingSoundInstance)) {
            return true;
        } else if (client.player != null && level.isClientSide()) {
            wordWallLoopingSoundInstance = new WordWallLoopingSoundInstance(wordWallBlockEntity,
                    SkyAboveVoiceWithinSounds.WORD_WALL_PASSIVE,
                    SoundSource.AMBIENT,
                    client.level.random);
            client.getSoundManager().play(wordWallLoopingSoundInstance);
            return true;
        } else client.getSoundManager().stop(wordWallLoopingSoundInstance);
        return false;
    }

    @Environment(EnvType.CLIENT) public WordWallLoopingSoundInstance wordWallLoopingSoundInstance;

    private void wordUnlockTick(ServerPlayer serverPlayer) {
        serverPlayer.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 30, 1, false, false));
        if (this.ticksUntilUnlock > 0 && level != null) {
            if (serverPlayer.blockPosition().distChessboard(this.getBlockPos()) < 4){
                this.ticksUntilUnlock--;
            }
            if (this.ticksUntilUnlock == 0) {

                serverPlayer.removeEffect(MobEffects.BLINDNESS);

                PlayerData playerState = StateSaverAndLoader.getPlayerState(serverPlayer);

                tryUnlockWord(serverPlayer, playerState);

                ServerPlayNetworking.send(serverPlayer, new UnlockedWordsPayload(playerState.unlockedWords));
                ServerPlayNetworking.send(serverPlayer, new WordWallRemovedPayload(true));

                level.setBlock(this.getBlockPos(), SMART_TO_DUMB_BLOCK_MAP.get(level.getBlockState(this.getBlockPos()).getBlock()).defaultBlockState(), 3);
            }
        }
    }



    private void trySpawnParticles(Player closestPlayer) {
        if (getLevel() instanceof ServerLevel serverLevel && closestPlayer instanceof ServerPlayer serverPlayer && hasClearLineOfSight(serverPlayer)) {
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
    }

    private void tryUnlockWord(ServerPlayer serverPlayer, PlayerData playerState) {
        if (!new HashSet<>(playerState.unlockedWords).containsAll(ALL_WORDS) && level != null) {
            ShoutWord wordToUnlock = null;
            AbstractShout shoutOfWord = null;
            while (wordToUnlock == null || playerState.unlockedWords.contains(wordToUnlock)) {
                while (shoutOfWord == null || this.level.dimension() != shoutOfWord.dimension()) {
                    shoutOfWord = Shouts.getRandomShout(level);
                }
                wordToUnlock = shoutOfWord.tryUnlockWord(playerState.unlockedWords);
            }
            playerState.unlockedWords.add(wordToUnlock);
            serverPlayer.connection.send(new ClientboundSetTitlesAnimationPacket(30, 60, 30));

            serverPlayer.connection.send(new ClientboundSetTitleTextPacket(Component.literal("NEW WORD LEARNED")));
            serverPlayer.connection.send(new ClientboundSetSubtitleTextPacket(Component.literal(
                    wordToUnlock.getName() + " - " + wordToUnlock.getMeaning() + ", " + shoutOfWord.getName())));
        }
    }

    private boolean hasClearLineOfSight(ServerPlayer player) {
        Vec3 playerEyes = player.getEyePosition(1.0F);
        Vec3 blockCenter = Vec3.atCenterOf(getBlockPos());

        assert level != null;
        BlockHitResult hitResult = level.clip(new ClipContext(
                playerEyes, blockCenter, ClipContext.Block.VISUAL, ClipContext.Fluid.NONE, player
        ));

        // Returns true if nothing is blocking the view OR if the first hit is the block itself.
        // Fixme make this a bit less strict?
        return hitResult.getType() == HitResult.Type.MISS || hitResult.getBlockPos().equals(getBlockPos());
    }

}
