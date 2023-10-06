package edu.project1.hangman;

public final class Dictionary {
    private static final String[] DICTIONARY = new String[] {"Society", "Trip"};

    private Dictionary() {
    }

    private static int getRandomIndex(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public static String getRandomWord() {
        return DICTIONARY[getRandomIndex(0, DICTIONARY.length - 1)];
    }
}
