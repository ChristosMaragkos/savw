package com.savw.shout;

import com.savw.networking.WhirlwindSprintS2CPayload;
import com.savw.word.ShoutWord;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public final class WhirlwindSprintShout extends AbstractShout{


    private WhirlwindSprintShout(String name, String description, ShoutWord firstWord, ShoutWord secondWord, ShoutWord thirdWord, ResourceLocation iconLocation, ResourceKey<Level> dimension) {
        super(name, description, firstWord, secondWord, thirdWord, iconLocation, dimension);
    }

    @Override
    public void useShout(Player player, Level level, int wordsUsed) {
        if (player instanceof ServerPlayer serverPlayer) {
            level.playSound(null, player.blockPosition(),
                    SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS, 1.0f, 1.0f);
            level.playSound(null, player.blockPosition(),
                    SoundEvents.WIND_CHARGE_BURST.value(), SoundSource.PLAYERS, 1.0f, 1.0f);
            if (level instanceof ServerLevel serverLevel) {
                Vec3[] bodyOffsets = {
                        new Vec3(0, 1.8, 0),  // Head
                        new Vec3(0, 1.3, 0),  // Chest
                        new Vec3(0, 0.8, 0),  // Waist
                        new Vec3(-0.4, 1.3, 0), // Left Shoulder
                        new Vec3(0.4, 1.3, 0),  // Right Shoulder
                        new Vec3(-0.4, 0.8, 0), // Left Hip
                        new Vec3(0.4, 0.8, 0),  // Right Hip
                };

                for (Vec3 offset : bodyOffsets) {
                    serverLevel.sendParticles(ParticleTypes.DUST_PLUME,
                            player.getX() + offset.x,
                            player.getY() + offset.y,
                            player.getZ() + offset.z,
                            1,
                            0.05,
                            0.05,
                            0.05,
                            0f
                            );
                }
            }

            ServerPlayNetworking.send(serverPlayer, new WhirlwindSprintS2CPayload(wordsUsed));
        }
    }

}
