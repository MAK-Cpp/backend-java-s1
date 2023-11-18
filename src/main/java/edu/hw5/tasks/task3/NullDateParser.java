package edu.hw5.tasks.task3;

import java.time.LocalDate;
import java.util.Optional;

public class NullDateParser extends DateParser {
    @Override
    public Optional<LocalDate> parseDate(String string) {
        return Optional.empty();
    }
}
