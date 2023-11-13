package edu.hw5.tasks.task3;

import java.time.LocalDate;
import java.util.Optional;

public abstract class DateParser {
    public DateParser nextParser;

    public abstract Optional<LocalDate> parseDate(String string);
}
