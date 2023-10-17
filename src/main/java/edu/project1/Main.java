package edu.project1;

import edu.project1.hangman.Hangman;
import edu.project1.hangman.RandomDictionary;
import edu.project1.hangman.RealPlayer;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        Hangman game = new Hangman(new RandomDictionary(), new RealPlayer());
        game.play();
    }
}
