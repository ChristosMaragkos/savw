package com.savw.word;

import com.savw.SkyAboveVoiceWithin;

import java.util.List;

public class Words {

    public static final ShoutWord Fus = ShoutWord.create("Fus", 10, 5);

    public static final ShoutWord Ro = ShoutWord.create("Ro", 15, 10);

    public static final ShoutWord Dah = ShoutWord.create("Dah", 20, 15);

    public static final ShoutWord Yol = ShoutWord.create("Yol", 15, 10);

    public static final ShoutWord Toor = ShoutWord.create("Toor", 17, 12);

    public static final ShoutWord Shul = ShoutWord.create("Shul", 20, 15);

    public static final ShoutWord Feim = ShoutWord.create("Feim", 20, 17);

    public static final ShoutWord Zii = ShoutWord.create("Zii", 25, 20);

    public static final ShoutWord Gron = ShoutWord.create("Gron", 30, 25);

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
            Gron
    );

    public static ShoutWord getByName(String name) {
        return ALL_WORDS.stream()
                .filter(word -> word.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Unknown ShoutWord: " + name));
    }

}
