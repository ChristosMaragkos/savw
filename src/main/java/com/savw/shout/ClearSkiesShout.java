package com.savw.shout;

import com.savw.word.ShoutWord;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import static com.savw.shout.Shouts.STORM_CALL;

public final class ClearSkiesShout extends AbstractShout {

    private ClearSkiesShout(String name, String description, ShoutWord firstWord, ShoutWord secondWord, ShoutWord thirdWord, ResourceLocation iconLocation, ResourceKey<Level> dimension) {
        super(name, description, firstWord, secondWord, thirdWord, iconLocation, dimension);
    }

    @Override
    public void useShout(Player player, Level level, int wordsUsed) {
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.DUST_PLUME, player.getX(), player.getY() + 1, player.getZ(), wordsUsed * 5, 0.5, 0.5, 0.5, 0.1);
            serverLevel.playSound(null, player.blockPosition(), SoundEvents.BREEZE_WIND_CHARGE_BURST.value(), player.getSoundSource(), 3f, 1f);
            if (STORM_CALL.stormCallHandler != null && STORM_CALL.stormCallHandler.isStormActive) {
                STORM_CALL.stormCallHandler.isStormActive = false;
            }
            serverLevel.setWeatherParameters(0, 3000 * wordsUsed, false, false);
        }
    }

}
