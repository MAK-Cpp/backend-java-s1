package edu.hw4.tasks.task19;

public enum ValidationError {
    WRONG_NAME("wrong name"),
    WRONG_AGE("wrong age"),
    WRONG_HEIGHT("wrong height"),
    WRONG_WEIGHT("wrong weight");

    private final String errorMessage;

    ValidationError(final String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
