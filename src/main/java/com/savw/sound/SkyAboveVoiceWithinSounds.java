package com.savw.sound;

import com.savw.SkyAboveVoiceWithin;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class SkyAboveVoiceWithinSounds {

    public static final SoundEvent WORD_UNLOCKED_1 = registerSound("word_unlocked_1");
    public static final SoundEvent WORD_UNLOCKED_2 = registerSound("word_unlocked_2");
    public static final SoundEvent WORD_UNLOCKED_3 = registerSound("word_unlocked_3");
    public static final SoundEvent WORD_WALL_PASSIVE = registerSound("word_wall_passive");


    private static SoundEvent registerSound(String name) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(SkyAboveVoiceWithin.MOD_ID, name);
        return Registry.register(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(id));
    }

    public static void initialize() {
        SkyAboveVoiceWithin.LOGGER.info("Sky Above, Voice Within Sounds Initialized!");
    }
}
