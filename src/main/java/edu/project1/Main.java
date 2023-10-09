package edu.project1;

import edu.project1.hangman.Dictionary;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) {

        System.out.println(Dictionary.getRandomWord());
    }
}
