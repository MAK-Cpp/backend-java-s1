package edu.hw5.tasks.task1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Collection;

public final class ComputerClubAnalytic {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd, HH:mm");
    private static final int COUNT_MINUTES_IN_HOUR = 60;

    private ComputerClubAnalytic() {
    }

    @SuppressWarnings("checkstyle:RegexpSinglelineJava")
    public static void calculate(final Collection<String> dates) {
        final int averageTime = (int) dates.stream()
            .map(x -> {
                String[] splittedDates = x.split(" - ");
                try {
                    return Duration.between(
                        DATE_FORMAT.parse(splittedDates[0]).toInstant(),
                        DATE_FORMAT.parse(splittedDates[1]).toInstant()
                    ).toMinutes();
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }).mapToDouble(x -> x).average().orElse(0);
        final String averageHours = String.format("%02d", averageTime / COUNT_MINUTES_IN_HOUR);
        final String averageMinutes = String.format("%02d", averageTime % COUNT_MINUTES_IN_HOUR);
        System.out.println(averageHours + "h " + averageMinutes + "m");
    }
}
