package edu.hw5.tasks.task3;

import java.time.LocalDate;
import java.util.Optional;

public final class CombinedDateParsers {
    private static final NullDateParser NULL_DATE_PARSER = new NullDateParser();
    private static final FourthDateParser FOURTH_DATE_PARSER = new FourthDateParser(NULL_DATE_PARSER);
    private static final ThirdDateParser THIRD_DATE_PARSER = new ThirdDateParser(FOURTH_DATE_PARSER);
    private static final SecondDateParser SECOND_DATE_PARSER = new SecondDateParser(THIRD_DATE_PARSER);
    private static final FirstDateParser FIRST_DATE_PARSER = new FirstDateParser(SECOND_DATE_PARSER);

    private CombinedDateParsers() {
    }

    public static Optional<LocalDate> parseDate(String string) {
        return FIRST_DATE_PARSER.parseDate(string);
    }
}
