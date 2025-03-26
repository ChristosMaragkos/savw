package com.savw.shout;

import com.savw.SkyAboveVoiceWithin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import java.util.List;

import static com.savw.word.Words.*;

public class Shouts {

    public static final UnrelentingForceShout UNRELENTING_FORCE = AbstractShout.createShout(
            UnrelentingForceShout.class,
            "Unrelenting Force",
            "A powerful shoutToSwitchTo that can knock back enemies.",
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
            ResourceLocation.fromNamespaceAndPath(SkyAboveVoiceWithin.MOD_ID, "textures/gui/sprites/fire_breath.png")
    );

    public static final BecomeEtherealShout BECOME_ETHEREAL = AbstractShout.createShout(
            BecomeEtherealShout.class,
            "Become Ethereal",
            "The Thu'um reaches out to the Void, changing your form to one that cannot harm, or be harmed.",
            Feim,
            Zii,
            Gron,
            ResourceLocation.fromNamespaceAndPath(SkyAboveVoiceWithin.MOD_ID, "textures/gui/sprites/unrelenting_force.png")
    );

    public static void initialize() {
        SkyAboveVoiceWithin.LOGGER.info("Shouts initialized!");

    }

    public static final List<AbstractShout> ALL_SHOUTS = List.of(
            UNRELENTING_FORCE,
            FIRE_BREATH,
            BECOME_ETHEREAL
    );

    public static AbstractShout getRandomShout(Level level) {
        return ALL_SHOUTS.get(level.random.nextIntBetweenInclusive(0, ALL_SHOUTS.size() - 1));
    }

    public static AbstractShout getByName(String name) {
        return ALL_SHOUTS.stream().filter(shout -> shout.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Unknown Shout: " + name));
    }
}
