package edu.hw5.tasks.task3;

import java.time.LocalDate;
import java.util.Optional;

public class ThirdDateParser extends DateParser {
    public ThirdDateParser(DateParser nextParser) {
        this.nextParser = nextParser;
    }

    @Override
    public Optional<LocalDate> parseDate(String string) {
        return switch (string) {
            case "tomorrow" -> Optional.of(LocalDate.now().plusDays(1));
            case "today" -> Optional.of(LocalDate.now());
            case "yesterday" -> Optional.of(LocalDate.now().minusDays(1));
            default -> nextParser.parseDate(string);
        };
    }
}
