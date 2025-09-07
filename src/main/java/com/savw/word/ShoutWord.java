package com.savw.word;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.savw.SkyAboveVoiceWithin;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

/// # ShoutWord
/// One of the key components of the mod.
/// It represents a word of power that can be unlocked and used by the player.
/// Each word has a name, a cooldown time, and a cost to unlock.
/// Words are singleton instances, meaning that there is only one instance of each word in the game.
/// This is achieved by using a private constructor and a static factory method.
/// @see com.savw.word.Words
/// @see com.savw.word.Words#ALL_WORDS
public class ShoutWord {

    private final String name;
    private final int cooldown;
    private final int costToUnlock;
    private final String meaning;

    /// The astute observer will notice that the constructor is private.
    /// This is indicative of the fact that this class is a singleton.
    /// To create your own ShoutWord instance, use the static factory method `create`
    /// and pass its output into a static final field.
    ///
    ///
    /// Literally disregard whatever the hell past me just wrote here.
    /// I meant to say that INSTANCES of ShoutWord are singletons.
    /// Meaning that there is only one instance of each word in the game.
    private ShoutWord(String name, int cooldown, int costToUnlock, String meaning){
        this.name = name;
        this.cooldown = cooldown;
        this.costToUnlock = costToUnlock;
        this.meaning = meaning;
    }

    /// ### create
    /// Static factory method to create a new instance of ShoutWord.
    ///
    /// For an example on how to use this method, see the `Words` class.
    ///
    /// @param name the name of the word
    /// @param cooldown the cooldown time for the word in seconds
    /// @param costToUnlock the cost to unlock the word
    /// @param meaning the meaning of the word, as translated from the Dragon Language
    /// @return a new instance of ShoutWord.
    /// @implNote The cooldown parameter is in ***seconds, NOT ticks.*** Conversion to ticks
    /// is handled within the registration of {@link com.savw.networking.UseShoutC2SPayload UseShoutC2SPayload},
    /// where the cooldown parameter is multiplied by 20
    /// and passed to the Player Data.
    /// @see com.savw.word.Words
    /// @see SkyAboveVoiceWithin#onInitialize
    public static ShoutWord create(String name, int cooldown, int costToUnlock, String meaning) {
        return new ShoutWord(name, cooldown, costToUnlock, meaning);
    }

    public String getName() {
        return this.name;
    }

    public int getCooldown() {
        return this.cooldown;
    }

    public int getCostToUnlock() {
        return this.costToUnlock;
    }

    public String getMeaning() {
        return this.meaning;
    }

    /// ### unlockWordWithExperience
    ///
    /// Probably will not be removed, but I won't look to implement
    /// having to acquire AND unlock words as two different processes.
    /// The original plan was something closer to Skyrim - You find a Word Wall, learn the Word,
    /// then you have to unlock it with XP.
    @Deprecated(forRemoval = true)
    public void unlockWordWithExperience(Player player){
        int currentExperienceLevel = player.experienceLevel;
        if (currentExperienceLevel>= costToUnlock) {
            player.giveExperienceLevels(-costToUnlock);
            player.displayClientMessage(Component.literal("You have unlocked the word: " + name), true);
            System.out.println("Word " + name + " unlocked!");
        } else {
            System.out.println("Not enough experience levels to unlock " + name);
        }
    }

    /// ### CODEC
    /// This is the codec for the ShoutWord class.
    /// @implSpec Wherever this is used, make sure to map it to the correct ShoutWord singleton instance.
    /// You don't want to create a new instance of the same ShoutWord every time you read it from saved data.
    public static final Codec<ShoutWord> CODEC = RecordCodecBuilder.create(shoutWordInstance -> shoutWordInstance.group(
            Codec.STRING.fieldOf("name").forGetter(ShoutWord::getName),
            Codec.INT.fieldOf("cooldown").forGetter(ShoutWord::getCooldown),
            Codec.INT.fieldOf("costToUnlock").forGetter(ShoutWord::getCostToUnlock),
            Codec.STRING.fieldOf("meaning").forGetter(ShoutWord::getMeaning)
    ).apply(shoutWordInstance, ShoutWord::create));

}