package com.savw.registry;

import com.savw.shout.AbstractShout;
import com.savw.word.ShoutWord;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

import static com.savw.SkyAboveVoiceWithin.LOGGER;
import static com.savw.SkyAboveVoiceWithin.withModId;

public class SkyAboveVoiceWithinRegistries {

    private static final ResourceKey<Registry<AbstractShout>> SHOUT_REGISTRY_KEY = ResourceKey.createRegistryKey(
            withModId("shouts")
    );

    private static final ResourceKey<Registry<ShoutWord>> WORD_REGISTRY_KEY = ResourceKey.createRegistryKey(
            withModId("words")
    );

    public static final Registry<AbstractShout> SHOUTS = FabricRegistryBuilder.createSimple(SHOUT_REGISTRY_KEY)
            .attribute(RegistryAttribute.MODDED)
            .buildAndRegister();

    public static final Registry<ShoutWord> WORDS = FabricRegistryBuilder.createSimple(WORD_REGISTRY_KEY)
            .attribute(RegistryAttribute.MODDED)
            .buildAndRegister();

    public static void initialize() {
        LOGGER.info("Shout and Word registries initialized.");
    }
}
