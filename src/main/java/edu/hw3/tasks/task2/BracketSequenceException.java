package edu.hw3.tasks.task2;

public class BracketSequenceException extends RuntimeException {
    public BracketSequenceException(final String message) {
        super(message);
    }

    public BracketSequenceException(final String message, final Throwable e) {
        super(message, e);
    }
}
