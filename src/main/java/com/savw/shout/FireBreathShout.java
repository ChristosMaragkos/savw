package com.savw.shout;

import com.savw.SkyAboveVoiceWithin;
import com.savw.word.ShoutWord;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class FireBreathShout extends AbstractShout{

    private FireBreathShout(String name, String description, ShoutWord firstWord, ShoutWord secondWord, ShoutWord thirdWord, ResourceLocation iconLocation) {
        super(name, description, firstWord, secondWord, thirdWord, iconLocation);
    }

    @Override
    public void useShout(Player player, Level level) {
        SkyAboveVoiceWithin.LOGGER.info("Using shout: {}", getName());
    }

}
