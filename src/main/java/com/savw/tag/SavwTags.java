package com.savw.tag;

import com.savw.SkyAboveVoiceWithin;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class SavwTags {

    public static final TagKey<EntityType<?>> ANIMAL_ALLEGIANCE_1_TARGETS = TagKey.create(
        Registries.ENTITY_TYPE,
        SkyAboveVoiceWithin.withModId("animal_allegiance_1_targets")
    );

    public static final TagKey<EntityType<?>> ANIMAL_ALLEGIANCE_2_TARGETS = TagKey.create(
        Registries.ENTITY_TYPE,
        SkyAboveVoiceWithin.withModId("animal_allegiance_2_targets")
    );

    public static final TagKey<EntityType<?>> ANIMAL_ALLEGIANCE_3_TARGETS = TagKey.create(
        Registries.ENTITY_TYPE,
        SkyAboveVoiceWithin.withModId("animal_allegiance_3_targets")
    );
}
