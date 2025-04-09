package com.savw.shout;

import com.savw.SkyAboveVoiceWithin;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import java.util.List;

import static com.savw.SkyAboveVoiceWithin.withModId;
import static com.savw.registry.SkyAboveVoiceWithinRegistries.SHOUTS;
import static com.savw.word.Words.*;

@SuppressWarnings("unused")
public final class Shouts {

    /// Dummy Shout to avoid null pointer exceptions when registering new PlayerData.
    /// This is not a real shout, but it is used to avoid using null as an initial shout.
    public static final DummyInitialShout DUMMY_INITIAL_SHOUT = registerShout(AbstractShout.createShout(
            DummyInitialShout.class,
            "Dummy Initial Shout",
            "No Shout Selected!",
            DummyWord1,
            DummyWord2,
            DummyWord3,
            null
    ));

    public static final UnrelentingForceShout UNRELENTING_FORCE = registerShout(AbstractShout.createShout(
            UnrelentingForceShout.class,
            "Unrelenting Force",
            "Your Voice is raw power, \npushing aside anything - or anyone - who stands in your path.",
            Fus,
            Ro,
            Dah,
            ResourceLocation.fromNamespaceAndPath(SkyAboveVoiceWithin.MOD_ID, "textures/gui/sprites/unrelenting_force.png")
    ));

    public static final FireBreathShout FIRE_BREATH = registerShout(AbstractShout.createShout(
            FireBreathShout.class,
            "Fire Breath",
            "Inhale air, exhale flame, and behold the Thu'um as inferno.",
            Yol,
            Toor,
            Shul,
            ResourceLocation.fromNamespaceAndPath(SkyAboveVoiceWithin.MOD_ID, "textures/gui/sprites/placeholder.png")
    ));

    public static final BecomeEtherealShout BECOME_ETHEREAL = registerShout(AbstractShout.createShout(
            BecomeEtherealShout.class,
            "Become Ethereal",
            "The Thu'um reaches out to the Void, \nchanging your form to one that cannot harm, or be harmed.",
            Feim,
            Zii,
            Gron,
            ResourceLocation.fromNamespaceAndPath(SkyAboveVoiceWithin.MOD_ID, "textures/gui/sprites/placeholder.png")
    ));

    public static final FrostBreathShout FROST_BREATH = registerShout(AbstractShout.createShout(
            FrostBreathShout.class,
            "Frost Breath",
            "Your breath is winter, your Thu'um a blizzard.",
            Fo,
            Krah,
            Diin,
            ResourceLocation.fromNamespaceAndPath(SkyAboveVoiceWithin.MOD_ID, "textures/gui/sprites/placeholder.png")
    ));

    public static final StormCallShout STORM_CALL = registerShout(AbstractShout.createShout(
            StormCallShout.class,
            "Storm Call",
            "A Shout to the skies, a cry to the clouds, \nthat awakens the destructive force of the land's lightning.",
            Strun,
            Bah,
            Qo,
            ResourceLocation.fromNamespaceAndPath(SkyAboveVoiceWithin.MOD_ID, "textures/gui/sprites/placeholder.png")
    ));

    public static final ClearSkiesShout CLEAR_SKIES = registerShout(AbstractShout.createShout(
            ClearSkiesShout.class,
            "Clear Skies",
            "The land itself yields before the Thu'um, \nas you clear away fog and inclement weather.",
            Lok,
            Vah,
            Koor,
            ResourceLocation.fromNamespaceAndPath(SkyAboveVoiceWithin.MOD_ID, "textures/gui/sprites/placeholder.png")
    ));

    public static final WhirlwindSprintShout WHIRLWIND_SPRINT = registerShout(AbstractShout.createShout(
            WhirlwindSprintShout.class,
            "Whirlwind Sprint",
            "The Thu'um rushes forward, carrying you \nin its wake with the speed of a tempest.",
            Wuld,
            Nah,
            Kest,
            ResourceLocation.fromNamespaceAndPath(SkyAboveVoiceWithin.MOD_ID, "textures/gui/sprites/placeholder.png")
    ));

    public static final DrainVitalityShout DRAIN_VITALITY = registerShout(AbstractShout.createShout(
            DrainVitalityShout.class,
            "Drain Vitality",
            "Coax both magical and mortal energies\nfrom your hapless opponent.",
            Gaan,
            Lah,
            Haas,
            ResourceLocation.fromNamespaceAndPath(SkyAboveVoiceWithin.MOD_ID, "textures/gui/sprites/placeholder.png")
    ));

    public static void initialize() {
        SkyAboveVoiceWithin.LOGGER.info("Shouts initialized!");
    }

    public static final List<AbstractShout> ALL_SHOUTS = SHOUTS.stream().map(shout -> {
        if (shout == DUMMY_INITIAL_SHOUT) {
            return null;
        }
        return shout;
    }).toList();


    public static final List<AbstractShout> ALL_SHOUTS_FOR_CODEC = SHOUTS.stream().toList();

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

    private static <S extends AbstractShout> S registerShout(S shout){
        return Registry.register(SHOUTS,
                withModId(shout.getName().toLowerCase().replaceAll(" ", "_")),
                shout
        );
    }

}
