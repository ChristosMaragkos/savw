package com.savw.shout;

import com.savw.entity.projectile.FrostShockwave;
import com.savw.word.ShoutWord;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public final class FrostBreathShout extends AbstractShout {

    private FrostBreathShout(String name, String description, ShoutWord firstWord, ShoutWord secondWord, ShoutWord thirdWord, ResourceLocation iconLocation, ResourceKey<Level> dimension) {
        super(name, description, firstWord, secondWord, thirdWord, iconLocation, dimension);
    }

    @Override
    public void useShout(Player player, Level level, int wordsUsed) {
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.playSound(null, player.blockPosition(), SoundEvents.GLASS_BREAK, player.getSoundSource(), 1f, 0.8f);
            serverLevel.playSound(null, player.blockPosition(), SoundEvents.BREEZE_WIND_CHARGE_BURST.value(), player.getSoundSource());
            serverLevel.addFreshEntity(new FrostShockwave(serverLevel, player, wordsUsed));
        }
    }
}
