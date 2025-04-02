package com.savw.word;

import com.savw.SkyAboveVoiceWithin;

import java.util.List;


@SuppressWarnings("SpellCheckingInspection")
public final class Words {

    /// ##### Dummy words to avoid using null as an initial Shout.
    public static final ShoutWord DummyWord1 = ShoutWord.create("DummyWord1", 0, 0, "");
    public static final ShoutWord DummyWord2 = ShoutWord.create("DummyWord2", 0, 0, "");
    public static final ShoutWord DummyWord3 = ShoutWord.create("DummyWord3", 0, 0, "");

    /// #### The words of power that are used in the mod.
    /// #### These are the words that the player can unlock and use in their shouts.
    public static final ShoutWord Fus = ShoutWord.create("Fus", 10, 5, "Force");

    public static final ShoutWord Ro = ShoutWord.create("Ro", 15, 10, "Balance");

    public static final ShoutWord Dah = ShoutWord.create("Dah", 20, 15, "Push");

    public static final ShoutWord Yol = ShoutWord.create("Yol", 15, 10, "Fire");

    public static final ShoutWord Toor = ShoutWord.create("Toor", 17, 12, "Inferno");

    public static final ShoutWord Shul = ShoutWord.create("Shul", 20, 15, "Sun");

    public static final ShoutWord Feim = ShoutWord.create("Feim", 20, 17, "Fade");

    public static final ShoutWord Zii = ShoutWord.create("Zii", 25, 20, "Spirit");

    public static final ShoutWord Gron = ShoutWord.create("Gron", 30, 25, "Bind");

    public static final ShoutWord Fo = ShoutWord.create("Fo", 15, 20, "Frost");

    public static final ShoutWord Krah = ShoutWord.create("Krah", 17, 25, "Cold");

    public static final ShoutWord Diin = ShoutWord.create("Diin", 20, 30, "Freeze");

    public static final ShoutWord Strun = ShoutWord.create("Strun", 1, 35, "Storm");

    public static final ShoutWord Bah = ShoutWord.create("Bah", 1, 40, "Wrath");

    public static final ShoutWord Qo = ShoutWord.create("Qo", 1, 45, "Lightning");

    public static final ShoutWord Lok = ShoutWord.create("Lok", 6, 10, "Sky");

    public static final ShoutWord Vah = ShoutWord.create("Vah", 8, 12, "Spring");

    public static final ShoutWord Koor = ShoutWord.create("Koor", 10, 15, "Summer");

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
            Koor
    );

    public static ShoutWord getByName(String name) {
        return ALL_WORDS.stream()
                .filter(word -> word.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Unknown ShoutWord: " + name));
    }

}
