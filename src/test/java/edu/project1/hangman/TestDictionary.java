package edu.project1.hangman;

import org.jetbrains.annotations.NotNull;

public class TestDictionary extends CorrectDictionary {
    private final static String[] testWords = new String[]{"TestWord", "abracadabra", "ITMO"};
    private final int wordId;

    public TestDictionary(int wordId) {
        this.wordId = wordId;
    }

    @Override
    @NotNull String getRandomWordWithoutCheck() {
        return testWords[wordId];
    }
}
