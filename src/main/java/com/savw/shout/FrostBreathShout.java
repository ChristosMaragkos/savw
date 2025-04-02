package com.savw.shout;

import com.savw.entity.projectile.FrostShockwave;
import com.savw.word.ShoutWord;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public final class FrostBreathShout extends AbstractShout {

    private FrostBreathShout(String name, String description, ShoutWord firstWord, ShoutWord secondWord, ShoutWord thirdWord, ResourceLocation iconLocation) {
        super(name, description, firstWord, secondWord, thirdWord, iconLocation);
    }

    @Override
    public void useShout(Player player, Level level, int wordsUsed) {
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.playSound(null, player.blockPosition(), SoundEvents.POWDER_SNOW_BREAK, player.getSoundSource());
            serverLevel.addFreshEntity(new FrostShockwave(serverLevel, player, wordsUsed));
        }
    }
}
