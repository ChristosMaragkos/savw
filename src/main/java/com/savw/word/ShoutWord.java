package com.savw.word;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class ShoutWord {

    private final String name;
    private final int cooldown;
    private final int costToUnlock;

    private ShoutWord(String name, int cooldown, int costToUnlock){
        this.name = name;
        this.cooldown = cooldown;
        this.costToUnlock = costToUnlock;
    }

    public static ShoutWord create(String name, int cooldown, int costToUnlock){
        return new ShoutWord(name, cooldown, costToUnlock);
    }

    public String getName() {
        return this.name;
    }

    public int getCooldown() {
        return cooldown;
    }

    public int getCostToUnlock() {
        return costToUnlock;
    }

    public void unlockWord(Player player, Level level){
        int currentExperienceLevel = player.experienceLevel;
        if (currentExperienceLevel>= costToUnlock) {
            player.giveExperienceLevels(-costToUnlock);
            player.displayClientMessage(Component.literal("You have unlocked the word: " + name), true);;
            System.out.println("Word " + name + " unlocked!");
        } else {
            System.out.println("Not enough experience levels to unlock " + name);
        }
    }

    public static final Codec<ShoutWord> CODEC = RecordCodecBuilder.create(shoutWordInstance -> shoutWordInstance.group(
            Codec.STRING.fieldOf("name").forGetter(ShoutWord::getName),
            Codec.INT.fieldOf("cooldown").forGetter(ShoutWord::getCooldown),
            Codec.INT.fieldOf("costToUnlock").forGetter(ShoutWord::getCostToUnlock)
    ).apply(shoutWordInstance, ShoutWord::create));

}