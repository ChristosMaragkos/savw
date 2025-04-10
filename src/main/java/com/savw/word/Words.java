package com.savw.word;

import com.savw.SkyAboveVoiceWithin;
import com.savw.registry.SkyAboveVoiceWithinRegistries;
import net.minecraft.core.Registry;

import java.util.List;

import static com.savw.SkyAboveVoiceWithin.withModId;


@SuppressWarnings("SpellCheckingInspection")
public final class Words {

    /// ##### Dummy words to avoid using null as an initial Shout.
    public static final ShoutWord DummyWord1 = registerWord(ShoutWord.create("DummyWord1", 0, 0, ""));
    public static final ShoutWord DummyWord2 = registerWord(ShoutWord.create("DummyWord2", 0, 0, ""));
    public static final ShoutWord DummyWord3 = registerWord(ShoutWord.create("DummyWord3", 0, 0, ""));

    /// #### The words of power that are used in the mod.
    /// #### These are the words that the player can unlock and use in their shouts.
    public static final ShoutWord Fus = registerWord(ShoutWord.create("Fus", 10, 5, "Force"));

    public static final ShoutWord Ro = registerWord(ShoutWord.create("Ro", 15, 10, "Balance"));

    public static final ShoutWord Dah = registerWord(ShoutWord.create("Dah", 20, 15, "Push"));

    public static final ShoutWord Yol = registerWord(ShoutWord.create("Yol", 15, 10, "Fire"));

    public static final ShoutWord Toor = registerWord(ShoutWord.create("Toor", 17, 12, "Inferno"));

    public static final ShoutWord Shul = registerWord(ShoutWord.create("Shul", 20, 15, "Sun"));

    public static final ShoutWord Feim = registerWord(ShoutWord.create("Feim", 20, 17, "Fade"));

    public static final ShoutWord Zii = registerWord(ShoutWord.create("Zii", 25, 20, "Spirit"));

    public static final ShoutWord Gron = registerWord(ShoutWord.create("Gron", 30, 25, "Bind"));

    public static final ShoutWord Fo = registerWord(ShoutWord.create("Fo", 15, 20, "Frost"));

    public static final ShoutWord Krah = registerWord(ShoutWord.create("Krah", 17, 25, "Cold"));

    public static final ShoutWord Diin = registerWord(ShoutWord.create("Diin", 20, 30, "Freeze"));

    public static final ShoutWord Strun = registerWord(ShoutWord.create("Strun", 60, 35, "Storm"));

    public static final ShoutWord Bah = registerWord(ShoutWord.create("Bah", 100, 40, "Wrath"));

    public static final ShoutWord Qo = registerWord(ShoutWord.create("Qo", 140, 45, "Lightning"));

    public static final ShoutWord Lok = registerWord(ShoutWord.create("Lok", 6, 10, "Sky"));

    public static final ShoutWord Vah = registerWord(ShoutWord.create("Vah", 8, 12, "Spring"));

    public static final ShoutWord Koor = registerWord(ShoutWord.create("Koor", 10, 15, "Summer"));

    public static final ShoutWord Wuld = registerWord(ShoutWord.create("Wuld", 13, 20, "Whirlwind"));

    public static final ShoutWord Nah = registerWord(ShoutWord.create("Nah", 15, 25, "Fury"));

    public static final ShoutWord Kest = registerWord(ShoutWord.create("Kest", 17, 30, "Tempest"));

    public static final ShoutWord Gaan = registerWord(ShoutWord.create("Gaan", 20, 20, "Stamina"));

    public static final ShoutWord Lah = registerWord(ShoutWord.create("Lah", 25, 25, "Magicka"));

    public static final ShoutWord Haas = registerWord(ShoutWord.create("Haas", 30, 30, "Health"));

    public static final ShoutWord Krii = registerWord(ShoutWord.create("Krii", 40, 35, "Kill"));

    public static final ShoutWord Lun = registerWord(ShoutWord.create("Lun", 50, 40, "Leech"));

    public static final ShoutWord Aus = registerWord(ShoutWord.create("Aus", 60, 45, "Suffer"));

    public static void initialize() {
        SkyAboveVoiceWithin.LOGGER.info("Words initialized!");
    }

    public static final List<ShoutWord> ALL_WORDS = List.of(
            Fus,
            Ro,
            Dah,
            Yol,
            Toor,
            Shul,
            Feim,
            Zii,
            Gron,
            Fo,
            Krah,
            Diin,
            Strun,
            Bah,
            Qo,
            Lok,
            Vah,
            Koor,
            Wuld,
            Nah,
            Kest,
            Gaan,
            Lah,
            Haas,
            Krii,
            Lun,
            Aus
    );

    public static ShoutWord getByName(String name) {
        return ALL_WORDS.stream()
                .filter(word -> word.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Unknown ShoutWord: " + name));
    }

    private static ShoutWord registerWord(ShoutWord word) {
        return Registry.register(SkyAboveVoiceWithinRegistries.WORDS,
                withModId(word.getName().toLowerCase()),
                word);
    }
    
}
