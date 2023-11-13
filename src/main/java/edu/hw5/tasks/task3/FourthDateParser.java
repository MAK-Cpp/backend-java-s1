package edu.hw5.tasks.task3;

import java.time.LocalDate;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FourthDateParser extends DateParser {
    private static final Pattern DATE_FORMAT = Pattern.compile("^(\\d*) day(s)? ago$");

    public FourthDateParser(DateParser nextParser) {
        this.nextParser = nextParser;
    }

    @Override
    public Optional<LocalDate> parseDate(String string) {
        Matcher matcher = DATE_FORMAT.matcher(string);
        if (matcher.find()) {
            return Optional.of(LocalDate.now().minusDays(Integer.parseInt(matcher.group(1))));
        } else {
            return nextParser.parseDate(string);
        }
    }
}
