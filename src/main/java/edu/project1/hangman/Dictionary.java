package edu.project1.hangman;

import java.util.Random;

public final class Dictionary {
    private static final Random RANDOM = new Random();
    private static final String[] DICTIONARY =
        new String[] {"Society", "Trip", "Cucumber", "Mayonnaise", "Subway", "Soccer", "Watermelon", "Tinkoff",
            "Minecraft"};

    private Dictionary() {
    }

    public static String getRandomWord() {
        return DICTIONARY[RANDOM.nextInt(DICTIONARY.length)];
    }
}
