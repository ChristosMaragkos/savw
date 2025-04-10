package com.savw.shout;

import com.savw.entity.projectile.DeathShockwave;
import com.savw.word.ShoutWord;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public final class MarkedForDeathShout extends AbstractShout{

    private MarkedForDeathShout(String name, String description, ShoutWord firstWord, ShoutWord secondWord, ShoutWord thirdWord, ResourceLocation iconLocation, ResourceKey<Level> dimension) {
        super(name, description, firstWord, secondWord, thirdWord, iconLocation, dimension);
    }

    @Override
    public void useShout(Player player, Level level, int wordsUsed) {
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.WARDEN_ATTACK_IMPACT, player.getSoundSource(), 0.7f, 0.75f);
        DeathShockwave deathShockwave = new DeathShockwave((ServerLevel) level, player, wordsUsed);
        level.addFreshEntity(deathShockwave);
    }
}
