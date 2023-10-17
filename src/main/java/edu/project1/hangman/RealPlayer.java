package edu.project1.hangman;

import java.util.Scanner;

public class RealPlayer implements Player {
    private final Scanner sc;

    public RealPlayer() {
        sc = new Scanner(System.in);
    }

    @Override
    public String makeGuess(String guessedWord) {
        return sc.next().toLowerCase();
    }
}
