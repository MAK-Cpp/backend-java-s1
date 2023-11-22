package edu.project3.analyzer;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record LogRecord(String remoteAddress, String remoteUser, OffsetDateTime timeLocal, String request, int status,
                        int bodyBytesSent, String httpReferer, String httpUserAgent) {
    private static final Pattern RECORD_PATTERN =
        Pattern.compile("([^ ]+) - ([^ ]+) \\[([^]]+)] \"([^\"]+)\" (\\d+) (\\d+) \"([^\"]+)\" \"([^\"]+)\"");
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
        DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z").withLocale(Locale.ENGLISH);

    @SuppressWarnings("checkstyle:MagicNumber")
    public static LogRecord parse(final String toParse) {
        Matcher matcher = RECORD_PATTERN.matcher(toParse);
        boolean find = matcher.find();
        if (find) {
            return new LogRecord(
                matcher.group(1),
                matcher.group(2),
                OffsetDateTime.parse(matcher.group(3), DATE_TIME_FORMATTER),
                matcher.group(4),
                Integer.parseInt(matcher.group(5)),
                Integer.parseInt(matcher.group(6)),
                matcher.group(7),
                matcher.group(8)
            );
        }
        return null;
    }
}
