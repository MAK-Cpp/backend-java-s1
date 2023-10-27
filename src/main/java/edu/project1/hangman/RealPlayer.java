package edu.project1.hangman;

import java.util.Scanner;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("checkstyle:RegexpSinglelineJava")
public class RealPlayer implements Player {
    private final Scanner sc;

    public RealPlayer() {
        sc = new Scanner(System.in);
    }

    @Override
    public @NotNull String makeGuess(String guessedWord) {
        System.out.print("Make a guess (you can give up by writing 'concede'): ");
        return sc.next();
    }

    @Override
    public boolean askToPlayAgain() {
        char ans;
        do {
            System.out.print("Do you want to play again? (y/n): ");
            ans = sc.next().toLowerCase().charAt(0);
        } while (ans != 'y' && ans != 'n');
        return ans == 'y';
    }
}
