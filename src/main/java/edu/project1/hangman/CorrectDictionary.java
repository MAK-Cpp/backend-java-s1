package edu.project1.hangman;

import org.jetbrains.annotations.NotNull;

public abstract class CorrectDictionary implements Dictionary {
    @Override
    final public @NotNull String getRandomWord() {
        final String result = getRandomWordWithoutCheck();
        if (!result.matches("[a-zA-Z]+")) {
            throw new HangmanException("dictionary contains not allowed word(s)");
        }
        return result.toLowerCase();
    }

    abstract @NotNull String getRandomWordWithoutCheck();
}
