package edu.project1.hangman;

public class HangmanException extends RuntimeException {
    public HangmanException(String message) {
        super(message);
    }

    public HangmanException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
