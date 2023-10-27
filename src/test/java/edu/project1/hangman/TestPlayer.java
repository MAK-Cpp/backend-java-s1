package edu.project1.hangman;

import org.jetbrains.annotations.NotNull;

public class TestPlayer implements Player {
    private int id;
    private int countOfPlayAgain;
    private final String[] guesses;

    TestPlayer(final String[] guesses, final int playAgain) {
        this.guesses = guesses;
        this.countOfPlayAgain = playAgain;
    }

    @Override
    public @NotNull String makeGuess(String guessedWord) {
        return id == guesses.length ? "concede" : guesses[id++];
    }

    @Override
    public boolean askToPlayAgain() {
        boolean ans = countOfPlayAgain > 0;
        countOfPlayAgain--;
        return ans;
    }
}
