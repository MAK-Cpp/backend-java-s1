package edu.project1.hangman;

import java.util.Random;
import org.jetbrains.annotations.NotNull;

public final class RandomDictionary extends CorrectDictionary {
    private static final Random RANDOM = new Random();
    private static final String[] DICTIONARY =
        new String[] {"Society", "Trip", "Cucumber", "Mayonnaise", "Subway", "Soccer", "Watermelon", "Tinkoff",
            "Minecraft"};

    public RandomDictionary() {
    }

    @Override
    @NotNull String getRandomWordWithoutCheck() {
        return DICTIONARY[RANDOM.nextInt(DICTIONARY.length)];
    }
}
