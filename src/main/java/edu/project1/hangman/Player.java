package edu.project1.hangman;

import org.jetbrains.annotations.NotNull;

public interface Player {
    @NotNull String makeGuess(String guessedWord);

    boolean askToPlayAgain();
}
