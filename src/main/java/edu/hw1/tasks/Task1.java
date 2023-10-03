package edu.hw1.tasks;

public class Task1 {
    private Task1() {
    }

    public static int minutesToSeconds(final String time) {
        if (time.isEmpty() || time.charAt(0) == ':' || time.charAt(time.length() - 1) == ':') {
            return -1;
        }
        int minutes = 0;
        int seconds = 0;
        boolean isSeconds = false;
        for (char character : time.toCharArray()) {
            if (character == ':') {
                if (isSeconds) {
                    return -1;
                }
                isSeconds = true;
            } else if ('0' <= character && character <= '9') {
                if (isSeconds) {
                    seconds = seconds * 10 + (character - '0');
                    if (seconds >= 60) {
                        return -1;
                    }
                } else {
                    minutes = minutes * 10 + (character - '0');
                }
            } else {
                return -1;
            }
        }
        if (!isSeconds) {
            return -1;
        }
        return minutes * 60 + seconds;
    }
}
