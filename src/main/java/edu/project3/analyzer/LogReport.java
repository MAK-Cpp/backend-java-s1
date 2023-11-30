package edu.project3.analyzer;

import edu.prettyTable.Format;
import edu.prettyTable.IntegerBiFunction;
import edu.prettyTable.line.IntegerLine;
import edu.prettyTable.line.StringLine;
import edu.prettyTable.table.ColumnTypedTable;
import edu.prettyTable.table.RandomTypedTable;
import edu.prettyTable.table.Table;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.nio.file.Path;
import java.util.Formatter;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class LogReport {
    private int requestCount = 0;
    private final Table generalInformation =
        new RandomTypedTable("Общая информация", "Метрика", List.of(), List.of("Значение"));
    private final Table requestedResources =
        new ColumnTypedTable("Запрашиваемые ресурсы", "Ресурс", new IntegerLine("Количество"));
    private final Table responseCodes =
        new ColumnTypedTable("Коды ответа", "Код", new StringLine("Имя"), new IntegerLine("Количество"));
    private final Table mostCommonCommand =
        new ColumnTypedTable(
            "(Дополнительно) Частота встречи команд в запросах",
            "Команда",
            new IntegerLine("Количество")
        );
    private final ColumnTypedTable mostCommonHttpUserAgent =
        new ColumnTypedTable("(Дополнительно) Типы Http User Agent", "Имя", new IntegerLine("Количество"));
    private BigInteger summaryResponseSize = BigInteger.ZERO;
    private long countResponse = 0;
    private final Format formant;
    private final Path out;
    private static final Map<Integer, String> NGINX_CODES = Map.ofEntries(
        Map.entry(100, "CONTINUE"),
        Map.entry(101, "SWITCHING_PROTOCOLS"),
        Map.entry(102, "PROCESSING"),
        Map.entry(200, "OK"),
        Map.entry(201, "CREATED"),
        Map.entry(202, "ACCEPTED"),
        Map.entry(204, "NO_CONTENT"),
        Map.entry(206, "PARTIAL_CONTENT"),
        Map.entry(300, "SPECIAL_RESPONSE"),
        Map.entry(301, "MOVED_PERMANENTLY"),
        Map.entry(302, "MOVED_TEMPORARILY"),
        Map.entry(303, "SEE_OTHER"),
        Map.entry(304, "NOT_MODIFIED"),
        Map.entry(307, "TEMPORARY_REDIRECT"),
        Map.entry(400, "BAD_REQUEST"),
        Map.entry(401, "UNAUTHORIZED"),
        Map.entry(403, "FORBIDDEN"),
        Map.entry(404, "NOT_FOUND"),
        Map.entry(405, "NOT_ALLOWED"),
        Map.entry(408, "REQUEST_TIME_OUT"),
        Map.entry(409, "CONFLICT"),
        Map.entry(411, "LENGTH_REQUIRED"),
        Map.entry(412, "PRECONDITION_FAILED"),
        Map.entry(413, "REQUEST_ENTITY_TOO_LARGE"),
        Map.entry(414, "REQUEST_URI_TOO_LARGE"),
        Map.entry(415, "UNSUPPORTED_MEDIA_TYPE"),
        Map.entry(416, "RANGE_NOT_SATISFIABLE"),
        Map.entry(429, "TOO_MANY_REQUESTS"),
        Map.entry(444, "CLOSE"),
        Map.entry(494, "REQUEST_HEADER_TOO_LARGE"),
        Map.entry(495, "CERT_ERROR"),
        Map.entry(496, "NO_CERT"),
        Map.entry(497, "TO_HTTPS"),
        Map.entry(499, "CLIENT_CLOSED_REQUEST"),
        Map.entry(500, "INTERNAL_SERVER_ERROR"),
        Map.entry(501, "NOT_IMPLEMENTED"),
        Map.entry(502, "BAD_GATEWAY"),
        Map.entry(503, "SERVICE_UNAVAILABLE"),
        Map.entry(504, "GATEWAY_TIME_OUT"),
        Map.entry(507, "INSUFFICIENT_STORAGE")
    );
    private static final Pattern REQUEST_PATTERN = Pattern.compile("(\\w+) .*(/.*) HTTP");

    public LogReport(final Format format, String filenames, String startDate, String stopDate, Path out) {
        this.formant = format;
        this.out = out;
        generalInformation.addRow("Файл(-ы)", filenames);
        generalInformation.addRow("Начальная дата", startDate);
        generalInformation.addRow("Конечная дата", stopDate);
    }

    public void addRequest() {
        requestCount++;
    }

    public void parseRequest(final String request) {
        Matcher matcher = REQUEST_PATTERN.matcher(request);
        if (matcher.find()) {
            if (mostCommonCommand.containsRow(matcher.group(1))) {
                mostCommonCommand.update(matcher.group(1), "Количество", 1, IntegerBiFunction.ADD);
            } else {
                mostCommonCommand.addRow(matcher.group(1), 1);
            }
            if (requestedResources.containsRow(matcher.group(2))) {
                requestedResources.update(matcher.group(2), "Количество", 1, IntegerBiFunction.ADD);
            } else {
                requestedResources.addRow(matcher.group(2), 1);
            }
        }
    }

    public void addCode(final int code) {
        final String stringCode = Integer.toString(code);
        if (responseCodes.containsRow(stringCode)) {
            responseCodes.update(stringCode, "Количество", 1, IntegerBiFunction.ADD);
        } else {
            responseCodes.addRow(stringCode, NGINX_CODES.get(code), 1);
        }
    }

    public void addSize(final int size) {
        summaryResponseSize = summaryResponseSize.add(BigInteger.valueOf(size));
        countResponse++;
    }

    public void addHttpUserAgent(final String httpUserAgent) {
        if (mostCommonHttpUserAgent.containsRow(httpUserAgent)) {
            mostCommonHttpUserAgent.update(httpUserAgent, "Количество", 1, IntegerBiFunction.ADD);
        } else {
            mostCommonHttpUserAgent.addRow(httpUserAgent, 1);
        }
    }

    private int averageAnsSize() {
        return countResponse == 0 ? 0 : summaryResponseSize.divide(BigInteger.valueOf(countResponse)).intValue();
    }

    public void print(final String filename) {
        final File file;
        if (formant == Format.MARKDOWN) {
            file = out.resolve(filename + ".md").toFile();
        } else {
            file = out.resolve(filename + ".adoc").toFile();
        }
        try (Formatter formatter = new Formatter(file)) {
            generalInformation.addRow("Количество запросов", requestCount);
            generalInformation.addRow("Средний размер ответа", averageAnsSize() + "b");

            generalInformation.format(formant, formatter);
            requestedResources.format(formant, formatter);
            responseCodes.format(formant, formatter);
            mostCommonCommand.format(formant, formatter);
            mostCommonHttpUserAgent.format(formant, formatter);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
