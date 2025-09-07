package com.savw.shout;

import com.savw.SkyAboveVoiceWithin;
import com.savw.ai.AnimalAllegianceAttackGoal;
import com.savw.effect.SkyAboveVoiceWithinMobEffects;
import com.savw.tag.SavwTags;
import com.savw.word.ShoutWord;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class AnimalAllegianceShout extends AbstractShout{

    protected AnimalAllegianceShout(String name, String description, ShoutWord firstWord, ShoutWord secondWord, ShoutWord thirdWord, ResourceLocation iconLocation, ResourceKey<Level> dimension) {
        super(name, description, firstWord, secondWord, thirdWord, iconLocation, dimension);
    }

    @Override
    public void useShout(Player player, Level level, int wordsUsed) {
        level.playSound(null, player.blockPosition(), SoundEvents.CREAKING_DEATH, player.getSoundSource());

        AABB range = player.getBoundingBox().inflate(15*wordsUsed);
        int duration = 20 * 10 * wordsUsed; // 10 seconds per word used

        TagKey<EntityType<?>> tierTag = switch (wordsUsed) {
            case 1 -> SavwTags.ANIMAL_ALLEGIANCE_1_TARGETS;
            case 2 -> SavwTags.ANIMAL_ALLEGIANCE_2_TARGETS;
            case 3 -> SavwTags.ANIMAL_ALLEGIANCE_3_TARGETS;
            default -> null;
        };

        if (tierTag == null) {
            SkyAboveVoiceWithin.LOGGER.error("Invalid number of words used for Animal Allegiance Shout: {}", wordsUsed);
            return;
        }

        var affectedAnimals = level.getEntitiesOfClass(PathfinderMob.class,
                range,
                animal -> animal.getType().is(tierTag));

        SkyAboveVoiceWithin.LOGGER.info("Animal Allegiance Shout affected {} animals.", affectedAnimals.size());

        for (PathfinderMob animal : affectedAnimals) {
            AnimalAllegianceAttackGoal goal = new AnimalAllegianceAttackGoal(animal,
                    1.0,
                    true,
                    player,
                    wordsUsed);

            animal.goalSelector.addGoal(-10, goal);
            animal.addEffect(new MobEffectInstance(SkyAboveVoiceWithinMobEffects.ANIMAL_ALLEGIANCE_ENRAGED, duration, 0, false, true, true));
            goal.start();
        }
    }
}
