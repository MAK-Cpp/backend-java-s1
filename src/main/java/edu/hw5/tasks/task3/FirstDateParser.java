package edu.hw5.tasks.task3;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

public class FirstDateParser extends DateParser {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-d");

    public FirstDateParser(DateParser nextParser) {
        this.nextParser = nextParser;
    }

    @Override
    public Optional<LocalDate> parseDate(String string) {
        try {
            return Optional.of(LocalDate.parse(string, DATE_FORMAT));
        } catch (DateTimeParseException e) {
            return nextParser.parseDate(string);
        }
    }
}
