package edu.project1.hangman;

import java.util.Scanner;

public class RealPlayer implements Player {
    private final Scanner sc;

    public RealPlayer() {
        sc = new Scanner(System.in);
    }

    @Override
    public char makeGuess(String guessedWord) {
        String ans = sc.next();
        if (ans.equals("concede")) {
            return '\0';
        }
        while (ans.length() != 1 && !Character.isLetter(ans.charAt(0))) {
            ans = sc.next();
        }
        return ans.toLowerCase().charAt(0);
    }
}
