package edu.hw8.tasks.task3;

public class InterruptedRuntimeException extends RuntimeException {
    public InterruptedRuntimeException(String message, InterruptedException cause) {
        super(message, cause);
    }

    public InterruptedRuntimeException(InterruptedException cause) {
        super(cause);
    }
}
