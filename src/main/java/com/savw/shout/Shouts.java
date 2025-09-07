package com.savw.shout;

import com.savw.SkyAboveVoiceWithin;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import java.util.List;

import static com.savw.SkyAboveVoiceWithin.withModId;
import static com.savw.registry.SkyAboveVoiceWithinRegistries.SHOUTS;
import static com.savw.word.Words.*;
import static net.minecraft.world.level.Level.*;

@SuppressWarnings("unused")
public final class Shouts {

    /// Dummy Shout to avoid null pointer exceptions when registering new PlayerData.
    /// This is not a real shout, but it is used to avoid using null as an initial shout.
    /// As such, it is not registered in the SHOUTS registry to avoid confusion.
    public static final DummyInitialShout DUMMY_INITIAL_SHOUT = AbstractShout.createShout(
            DummyInitialShout.class,
            "Dummy Initial Shout",
            "null",
            DummyWord1,
            DummyWord2,
            DummyWord3,
            null,
            OVERWORLD
    );

    public static final UnrelentingForceShout UNRELENTING_FORCE = registerShout(AbstractShout.createShout(
            UnrelentingForceShout.class,
            "Unrelenting Force",
            "Your Voice is raw power, \npushing aside anything - or anyone - who stands in your path.",
            Fus,
            Ro,
            Dah,
            ResourceLocation.fromNamespaceAndPath(SkyAboveVoiceWithin.MOD_ID, "textures/gui/sprites/unrelenting_force.png"),
            OVERWORLD
    ));

    public static final FireBreathShout FIRE_BREATH = registerShout(AbstractShout.createShout(
            FireBreathShout.class,
            "Fire Breath",
            "Inhale air, exhale flame, and behold the Thu'um as inferno.",
            Yol,
            Toor,
            Shul,
            ResourceLocation.fromNamespaceAndPath(SkyAboveVoiceWithin.MOD_ID, "textures/gui/sprites/placeholder.png"),
            NETHER
    ));

    public static final BecomeEtherealShout BECOME_ETHEREAL = registerShout(AbstractShout.createShout(
            BecomeEtherealShout.class,
            "Become Ethereal",
            "The Thu'um reaches out to the Void, \nchanging your form to one that cannot harm, or be harmed.",
            Feim,
            Zii,
            Gron,
            ResourceLocation.fromNamespaceAndPath(SkyAboveVoiceWithin.MOD_ID, "textures/gui/sprites/placeholder.png"),
            END
    ));

    public static final FrostBreathShout FROST_BREATH = registerShout(AbstractShout.createShout(
            FrostBreathShout.class,
            "Frost Breath",
            "Your breath is winter, your Thu'um a blizzard.",
            Fo,
            Krah,
            Diin,
            ResourceLocation.fromNamespaceAndPath(SkyAboveVoiceWithin.MOD_ID, "textures/gui/sprites/placeholder.png"),
            OVERWORLD
    ));

    public static final StormCallShout STORM_CALL = registerShout(AbstractShout.createShout(
            StormCallShout.class,
            "Storm Call",
            "A Shout to the skies, a cry to the clouds, \nthat awakens the destructive force of the land's lightning.",
            Strun,
            Bah,
            Qo,
            ResourceLocation.fromNamespaceAndPath(SkyAboveVoiceWithin.MOD_ID, "textures/gui/sprites/placeholder.png"),
            OVERWORLD
    ));

    public static final ClearSkiesShout CLEAR_SKIES = registerShout(AbstractShout.createShout(
            ClearSkiesShout.class,
            "Clear Skies",
            "The land itself yields before the Thu'um, \nas you clear away fog and inclement weather.",
            Lok,
            Vah,
            Koor,
            ResourceLocation.fromNamespaceAndPath(SkyAboveVoiceWithin.MOD_ID, "textures/gui/sprites/placeholder.png"),
            OVERWORLD
    ));

    public static final WhirlwindSprintShout WHIRLWIND_SPRINT = registerShout(AbstractShout.createShout(
            WhirlwindSprintShout.class,
            "Whirlwind Sprint",
            "The Thu'um rushes forward, carrying you \nin its wake with the speed of a tempest.",
            Wuld,
            Nah,
            Kest,
            ResourceLocation.fromNamespaceAndPath(SkyAboveVoiceWithin.MOD_ID, "textures/gui/sprites/placeholder.png"),
            OVERWORLD
    ));

    public static final DrainVitalityShout DRAIN_VITALITY = registerShout(AbstractShout.createShout(
            DrainVitalityShout.class,
            "Drain Vitality",
            "Coax both magical and mortal energies\nfrom your hapless opponent.",
            Gaan,
            Lah,
            Haas,
            ResourceLocation.fromNamespaceAndPath(SkyAboveVoiceWithin.MOD_ID, "textures/gui/sprites/placeholder.png"),
            NETHER
    ));

    public static final MarkedForDeathShout MARKED_FOR_DEATH = registerShout(AbstractShout.createShout(
            MarkedForDeathShout.class,
            "Marked for Death",
            "Speak, and let your Voice herald doom, \nas an opponent's armor and lifeforce are weakened.",
            Krii,
            Lun,
            Aus,
            ResourceLocation.fromNamespaceAndPath(SkyAboveVoiceWithin.MOD_ID, "textures/gui/sprites/placeholder.png"),
            END
    ));

    public static final AnimalAllegianceShout ANIMAL_ALLEGIANCE = registerShout(AbstractShout.createShout(
            AnimalAllegianceShout.class,
            "Animal Allegiance",
            "A Shout for help from the beasts of the wild,\nwho come to fight in your defense.",
            Raan,
            Mir,
            Tah,
            ResourceLocation.fromNamespaceAndPath(SkyAboveVoiceWithin.MOD_ID, "textures/gui/sprites/placeholder.png"),
            OVERWORLD
    ));

    /// The shout list is initialized when the server starts.
    /// References to Shouts through Lists are derived dynamically from {@link com.savw.registry.SkyAboveVoiceWithinRegistries#SHOUTS the OVERWORLD_SHOUTS registry}.
    /// This is done to avoid the need to manually update the lists.
    /// This also finally allows other mods to add shouts of their own.
    /// @since Lists have been present since 0.0.1, but were not derived from the registry until 0.0.3.
    /// @implNote ALL_SHOUTS_FOR_CODEC list was removed in 0.0.3 due to it being redundant.
    /// I discovered a way to use {@link Shouts#DUMMY_INITIAL_SHOUT DUMMY_INITIAL_SHOUT} for its intended purposes
    /// while removing any and all ways of referring to it
    public static List<AbstractShout> ALL_SHOUTS;

    public static void initialize() {
        ServerLifecycleEvents.SERVER_STARTED.register(
                server -> ALL_SHOUTS = SHOUTS.stream().filter(shout -> shout != DUMMY_INITIAL_SHOUT)
                        .toList()
        );
        SkyAboveVoiceWithin.LOGGER.info("Shouts initialized!");
    }


    public static AbstractShout getRandomShout(Level level) {
        return ALL_SHOUTS.get(level.random.nextIntBetweenInclusive(0, ALL_SHOUTS.size() - 1));
    }

    /// This method is no longer deprecated as of 0.0.3! With the overhaul of shout encoding,
    /// getByNameToEncode(String name) became redundant and was removed, having all its usages replaced
    /// with this method.
    /// @since 0.0.1
    public static AbstractShout getShoutByName(String name) {
        return ALL_SHOUTS.stream().filter(shout -> shout.getName().equals(name))
                .findFirst()
                .orElse(DUMMY_INITIAL_SHOUT);
    }

    /// Will ***probably*** need to make this public if I am to allow other mods to add shouts.
    private static <S extends AbstractShout> S registerShout(S shout){
        return Registry.register(SHOUTS,
                withModId(shout.getName().toLowerCase().replaceAll(" ", "_")),
                shout
        );
    }

}
