package edu.hw8.tasks.task3;

public class InterruptedRuntimeException extends RuntimeException {
    public InterruptedRuntimeException() {
    }

    public InterruptedRuntimeException(String message) {
        super(message);
    }

    public InterruptedRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public InterruptedRuntimeException(Throwable cause) {
        super(cause);
    }
}
