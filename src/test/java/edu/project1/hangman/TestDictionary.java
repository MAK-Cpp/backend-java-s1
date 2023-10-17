package edu.project1.hangman;

import org.jetbrains.annotations.NotNull;

public class TestDictionary implements Dictionary {
    private final static String[] testWords = new String[]{"TestWord", "abracadabra", "ITMO"};
    private final int wordId;

    public TestDictionary(int wordId) {
        this.wordId = wordId;
    }

    @Override
    public @NotNull String getRandomWord() {
        return testWords[wordId];
    }
}
