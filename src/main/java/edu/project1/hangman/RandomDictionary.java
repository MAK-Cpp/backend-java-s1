package edu.project1.hangman;

import java.util.Random;
import org.jetbrains.annotations.NotNull;

public final class RandomDictionary implements Dictionary {
    private static final Random RANDOM = new Random();
    private static final String[] DICTIONARY =
        new String[] {"Society", "Trip", "Cucumber", "Mayonnaise", "Subway", "Soccer", "Watermelon", "Tinkoff",
            "Minecraft"};

    public RandomDictionary() {
    }

    @Override
    public @NotNull String getRandomWord() {
        return DICTIONARY[RANDOM.nextInt(DICTIONARY.length)];
    }
}
