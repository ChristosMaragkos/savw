package com.savw.shout;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.savw.word.ShoutWord;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static com.savw.shout.Shouts.ALL_SHOUTS;

public abstract class AbstractShout {

    private final String name;
    private final String description;
    private final ShoutWord firstWord;
    private final ShoutWord secondWord;
    private final ShoutWord thirdWord;
    private final ResourceLocation iconLocation;

    protected AbstractShout(String name, String description, ShoutWord firstWord, ShoutWord secondWord, ShoutWord thirdWord, ResourceLocation iconLocation) {
        this.name = name;
        this.description = description;
        this.firstWord = firstWord;
        this.secondWord = secondWord;
        this.thirdWord = thirdWord;
        this.iconLocation = iconLocation;
    }
    
    public static <S extends AbstractShout> S createShout(Class<S> shoutClass, String name, String description, ShoutWord firstWord, ShoutWord secondWord, ShoutWord thirdWord, ResourceLocation iconLocation) {
        try {
            Constructor<S> constructor = shoutClass.getDeclaredConstructor(String.class, String.class, ShoutWord.class, ShoutWord.class, ShoutWord.class, ResourceLocation.class);
            constructor.setAccessible(true); // Allow access to private constructor
            return constructor.newInstance(name, description, firstWord, secondWord, thirdWord, iconLocation);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Failed to create shoutToSwitchTo instance", e);
        }
    }

    public static final Codec<AbstractShout> SHOUT_NAME_CODEC = Codec.STRING.flatXmap(
            name -> ALL_SHOUTS.stream()
                    .filter(shout -> shout.getName().equals(name))
                    .findFirst()
                    .map(DataResult::success)
                    .orElseGet(() -> DataResult.error(() -> "Shout not found: " + name)),
            shout -> DataResult.success(shout.getName()) // Encoding just returns the name
    );

    public ShoutWord getFirstWord() {
        return this.firstWord;
    }

    public ShoutWord getSecondWord() {
        return this.secondWord;
    }

    public ShoutWord getThirdWord() {
        return this.thirdWord;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public ResourceLocation getIconLocation() {
        return this.iconLocation;
    }

    /// Abstract method to be implemented by subclasses
    public abstract void useShout(Player player, Level level);

public ShoutWord tryUnlockWord(List<ShoutWord> shoutWordList) {
    if (!shoutWordList.contains(getFirstWord())) {
        return getFirstWord();
    }
    if (!shoutWordList.contains(getSecondWord())) {
        return getSecondWord();
    }
    if (!shoutWordList.contains(getThirdWord())) {
        return getThirdWord();
    }
    return null;
}

}
