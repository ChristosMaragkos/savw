package com.savw.shout;

import com.savw.SkyAboveVoiceWithin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import java.util.List;

import static com.savw.word.Words.*;

public final class Shouts {

    /// Dummy Shout to avoid null pointer exceptions when registering new PlayerData.
    /// This is not a real shout, but it is used to avoid using null as an initial shout.
    public static final DummyInitialShout DUMMY_INITIAL_SHOUT = AbstractShout.createShout(
            DummyInitialShout.class,
            "Dummy Initial Shout",
            "No Shout Selected!",
            DummyWord1,
            DummyWord2,
            DummyWord3,
            null
    );

    public static final UnrelentingForceShout UNRELENTING_FORCE = AbstractShout.createShout(
            UnrelentingForceShout.class,
            "Unrelenting Force",
            "Your Voice is raw power, \npushing aside anything - or anyone - who stands in your path.",
            Fus,
            Ro,
            Dah,
            ResourceLocation.fromNamespaceAndPath(SkyAboveVoiceWithin.MOD_ID, "textures/gui/sprites/unrelenting_force.png")
    );

    public static final FireBreathShout FIRE_BREATH = AbstractShout.createShout(
            FireBreathShout.class,
            "Fire Breath",
            "Inhale air, exhale flame, and behold the Thu'um as inferno.",
            Yol,
            Toor,
            Shul,
            ResourceLocation.fromNamespaceAndPath(SkyAboveVoiceWithin.MOD_ID, "textures/gui/sprites/placeholder.png")
    );

    public static final BecomeEtherealShout BECOME_ETHEREAL = AbstractShout.createShout(
            BecomeEtherealShout.class,
            "Become Ethereal",
            "The Thu'um reaches out to the Void, \nchanging your form to one that cannot harm, or be harmed.",
            Feim,
            Zii,
            Gron,
            ResourceLocation.fromNamespaceAndPath(SkyAboveVoiceWithin.MOD_ID, "textures/gui/sprites/placeholder.png")
    );

    public static final FrostBreathShout FROST_BREATH = AbstractShout.createShout(
            FrostBreathShout.class,
            "Frost Breath",
            "Your breath is winter, your Thu'um a blizzard.",
            Fo,
            Krah,
            Diin,
            ResourceLocation.fromNamespaceAndPath(SkyAboveVoiceWithin.MOD_ID, "textures/gui/sprites/placeholder.png")
    );

    public static final StormCallShout STORM_CALL = AbstractShout.createShout(
            StormCallShout.class,
            "Storm Call",
            "A Shout to the skies, a cry to the clouds, \nthat awakens the destructive force of the land's lightning.",
            Strun,
            Bah,
            Qo,
            ResourceLocation.fromNamespaceAndPath(SkyAboveVoiceWithin.MOD_ID, "textures/gui/sprites/placeholder.png")
    );

    public static final ClearSkiesShout CLEAR_SKIES = AbstractShout.createShout(
            ClearSkiesShout.class,
            "Clear Skies",
            "The land itself yields before the Thu'um, \nas you clear away fog and inclement weather.",
            Lok,
            Vah,
            Koor,
            ResourceLocation.fromNamespaceAndPath(SkyAboveVoiceWithin.MOD_ID, "textures/gui/sprites/placeholder.png")
    );

    public static final WhirlwindSprintShout WHIRLWIND_SPRINT = AbstractShout.createShout(
            WhirlwindSprintShout.class,
            "Whirlwind Sprint",
            "The Thu'um rushes forward, carrying you \nin its wake with the speed of a tempest.",
            Wuld,
            Nah,
            Kest,
            ResourceLocation.fromNamespaceAndPath(SkyAboveVoiceWithin.MOD_ID, "textures/gui/sprites/placeholder.png")
    );

    public static void initialize() {
        SkyAboveVoiceWithin.LOGGER.info("Shouts initialized!");

    }

    public static final List<AbstractShout> ALL_SHOUTS = List.of(
            UNRELENTING_FORCE,
            FIRE_BREATH,
            FROST_BREATH,
            BECOME_ETHEREAL,
            STORM_CALL,
            WHIRLWIND_SPRINT,
            CLEAR_SKIES
    );

    public static final List<AbstractShout> ALL_SHOUTS_FOR_CODEC = List.of(
            UNRELENTING_FORCE,
            FIRE_BREATH,
            FROST_BREATH,
            BECOME_ETHEREAL,
            STORM_CALL,
            CLEAR_SKIES,
            WHIRLWIND_SPRINT,
            DUMMY_INITIAL_SHOUT
    );

    public static AbstractShout getRandomShout(Level level) {
        return ALL_SHOUTS.get(level.random.nextIntBetweenInclusive(0, ALL_SHOUTS.size() - 1));
    }

    @Deprecated(forRemoval = true)
    public static AbstractShout getByName(String name) {
        return ALL_SHOUTS.stream().filter(shout -> shout.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Unknown Shout: " + name));
    }

    public static AbstractShout getByNameToEncode(String name) {
        return ALL_SHOUTS_FOR_CODEC.stream().filter(shout -> shout.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Unknown Shout: " + name));
    }
}
