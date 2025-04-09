package com.savw.shout;

import com.savw.word.ShoutWord;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

/// # DummyInitialShout.java
/// This class is a placeholder for the initial shout that is used when no shout is selected, to avoid NullPointerExceptions.
public final class DummyInitialShout extends AbstractShout{

    private DummyInitialShout(String name, String description, ShoutWord firstWord, ShoutWord secondWord, ShoutWord thirdWord, ResourceLocation iconLocation, ResourceKey<Level> dimension) {
        super(name, description, firstWord, secondWord, thirdWord, iconLocation, dimension);
    }

    @Override
    public void useShout(Player player, Level level, int wordsUsed) {
        player.displayClientMessage(Component.literal("No Shout Selected!"), true);
    }
}
