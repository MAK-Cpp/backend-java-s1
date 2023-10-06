package edu.hw1.tasks;

public final class Task1 {
    private static final int COUNT_SECONDS_IN_MINUTE = 60;

    private Task1() {
    }

    public static int minutesToSeconds(final String time) {
        final String[] splittedTime = time.split(":");
        if (splittedTime.length != 2) {
            return -1;
        }
        try {
            final int minutes = Integer.parseInt(splittedTime[0]);
            final int seconds = Integer.parseInt(splittedTime[1]);
            if (minutes >= 0 && (0 <= seconds && seconds < COUNT_SECONDS_IN_MINUTE)) {
                return minutes * COUNT_SECONDS_IN_MINUTE + seconds;
            }
            return -1;
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
