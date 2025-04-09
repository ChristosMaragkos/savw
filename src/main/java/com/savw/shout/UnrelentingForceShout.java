package com.savw.shout;

import com.savw.entity.projectile.ForceShockwave;
import com.savw.word.ShoutWord;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

/// # UnrelentingForceShout.java
/// This class represents the Unrelenting Force shout, which is a powerful shout that creates a shockwave effect.
/// It extends the AbstractShout class and implements the useShout method to create the shockwave effect.
/// @see com.savw.shout.Shouts#UNRELENTING_FORCE
/// @see com.savw.word.Words#Fus
/// @see com.savw.word.Words#Ro
/// @see com.savw.word.Words#Dah
public final class UnrelentingForceShout extends AbstractShout{

    private UnrelentingForceShout(String name, String description, ShoutWord firstWord, ShoutWord secondWord, ShoutWord thirdWord, ResourceLocation iconLocation, ResourceKey<Level> dimension) {
        super(name, description, firstWord, secondWord, thirdWord, iconLocation, dimension);
    }

    @Override
    public void useShout(Player player, Level level, int wordsUsed) {
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BREEZE_WIND_CHARGE_BURST, player.getSoundSource(), 5f, 1f);
            serverLevel.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.GENERIC_EXPLODE, player.getSoundSource(), 5f, 1f);
            serverLevel.addFreshEntity(new ForceShockwave(serverLevel, player, wordsUsed));
        }
    }
}
