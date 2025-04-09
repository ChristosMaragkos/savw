package com.savw.loot_table;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;

import static com.savw.SkyAboveVoiceWithin.LOGGER;
import static com.savw.SkyAboveVoiceWithin.withModId;

public class SAVWLootTables {

    public static ResourceKey<LootTable> WORD_WALL_POT = ResourceKey.create(Registries.LOOT_TABLE, withModId("pots/word_wall"));

    public static void initialize() {
        LOGGER.info("Loot Tables Initialized");
    }
}
