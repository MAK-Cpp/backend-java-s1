package edu.hw5.tasks.task2;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

public final class NextFriday13th {
    private NextFriday13th() {
    }

    public static Date find(final Date from) {
        LocalDate date = from.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        while (date.getDayOfMonth() != 13) {
            date = date.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
        }
        return java.util.Date.from(date.atStartOfDay()
            .atZone(ZoneId.systemDefault())
            .toInstant());
    }
}
