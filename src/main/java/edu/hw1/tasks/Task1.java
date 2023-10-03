package edu.hw1.tasks;

public final class Task1 {
    static final private int COUNT_SECONDS_IN_MINUTE = 60;
    static final private int NUMBER_SYSTEM = 10;

    private Task1() {
    }

    public static int minutesToSeconds(final String time) {
        if (time.isEmpty() || time.charAt(0) == ':' || time.charAt(time.length() - 1) == ':') {
            return -1;
        }
        int minutes = 0;
        int seconds = 0;
        boolean isSeconds = false;
        boolean isException = false;
        for (char character : time.toCharArray()) {
            if (character == ':') {
                if (isSeconds) {
                    isException = true;
                    break;
                }
                isSeconds = true;
            } else if ('0' <= character && character <= '9') {
                if (isSeconds) {
                    seconds = seconds * NUMBER_SYSTEM + (character - '0');
                    if (seconds >= COUNT_SECONDS_IN_MINUTE) {
                        isException = true;
                        break;
                    }
                } else {
                    minutes = minutes * NUMBER_SYSTEM + (character - '0');
                }
            } else {
                isException = true;
                break;
            }
        }
        if (!isSeconds) {
            isException = true;
        }
        return isException ? -1 : minutes * COUNT_SECONDS_IN_MINUTE + seconds;
    }
}
