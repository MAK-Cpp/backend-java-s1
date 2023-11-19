package edu.project3.analyzer;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.nio.file.Path;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogReport {
    private int requestCount = 0;
    private final HashMap<String, Integer> mostFrequentlyRequestedResources = new HashMap<>();
    private final HashMap<Integer, Integer> mostCommonResponseCodes = new HashMap<>();
    private final HashMap<String, Integer> mostCommonCommand = new HashMap<>();
    private BigInteger summaryResponseSize = BigInteger.ZERO;
    private long countResponse = 0;
    private final Format formant;
    private final String filenames;
    private final String startDate;
    private final String stopDate;
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

    public LogReport(final Format format, String filename, String startDate, String stopDate) {
        this.formant = format;
        this.filenames = filename;
        this.startDate = startDate;
        this.stopDate = stopDate;
    }

    public void addRequest() {
        requestCount++;
    }

    public void parseRequest(final String request) {
        Matcher matcher = REQUEST_PATTERN.matcher(request);
        if (matcher.find()) {
            mostFrequentlyRequestedResources.merge(matcher.group(2), 1, Integer::sum);
            mostCommonCommand.merge(matcher.group(1), 1, Integer::sum);
        }
    }

    public void addCode(final int code) {
        mostCommonResponseCodes.merge(code, 1, Integer::sum);
    }

    public void addSize(final int size) {
        summaryResponseSize = summaryResponseSize.add(BigInteger.valueOf(size));
        countResponse++;
    }

    private int averageAnsSize() {
        return summaryResponseSize.divide(BigInteger.valueOf(countResponse)).intValue();
    }

    private static int max(int... values) {
        int ans = values[0];
        for (int i : values) {
            ans = Math.max(ans, i);
        }
        return ans;
    }

    private String getFormat(int... columnsWidth) {
        StringBuilder ans = new StringBuilder();
        for (int width : columnsWidth) {
            ans.append("| %").append(width);
            if (formant == Format.MARKDOWN) {
                ans.append("s ");
            } else {
                ans.append("s");
            }
        }
        if (formant == Format.MARKDOWN) {
            ans.append("|\n");
        } else {
            ans.append("\n");
        }
        return ans.toString();
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    private String getSeparator(int... columnsWidth) {
        StringBuilder ans = new StringBuilder();
        ans.append("|");
        for (int width : columnsWidth) {
            if (formant == Format.MARKDOWN) {
                ans.append(":").append("-".repeat(width)).append(":|");
            } else {
                ans.append("=".repeat(width + 3));
            }
        }
        ans.append('\n');
        return ans.toString();
    }

    private String getTitle(final String name) {
        if (formant == Format.MARKDOWN) {
            return "### " + name + "\n";
        }
        return "=== " + name + "\n";
    }

    @SuppressWarnings({"checkstyle:MultipleStringLiterals", "checkstyle:CyclomaticComplexity"})
    public void print(final Path directory, final String filename) {
        final File file;
        if (formant == Format.MARKDOWN) {
            file = directory.resolve(filename + ".md").toFile();
        } else {
            file = directory.resolve(filename + ".adoc").toFile();
        }
        try (Formatter formatter = new Formatter(file)) {
            // first info
            int firstColumnWidth = "Средний размер ответа".length();
            int secondColumnWidth = max(
                filenames.length(),
                startDate.length(),
                stopDate.length(),
                Integer.toString(requestCount).length(),
                Integer.toString(averageAnsSize()).length() + 1
            );
            formatter.format(getTitle("Общая информация"));
            if (formant == Format.ADOC) {
                formatter.format(getSeparator(firstColumnWidth, secondColumnWidth));
            }
            String format = getFormat(firstColumnWidth, secondColumnWidth);
            formatter.format(format, "Метрика", "Значение");
            if (formant == Format.MARKDOWN) {
                formatter.format(getSeparator(firstColumnWidth, secondColumnWidth));
            }
            formatter.format(format, "Файл(-ы)", filenames);
            formatter.format(format, "Начальная дата", startDate);
            formatter.format(format, "Конечная дата", stopDate);
            formatter.format(format, "Количество запросов", requestCount);
            formatter.format(format, "Средний размер ответа", averageAnsSize() + "b");
            if (formant == Format.ADOC) {
                formatter.format(getSeparator(firstColumnWidth, secondColumnWidth));
            }

            // resources
            firstColumnWidth = "Ресурс".length();
            secondColumnWidth = "Количество".length();
            for (String key : mostFrequentlyRequestedResources.keySet()) {
                firstColumnWidth = Math.max(firstColumnWidth, key.length() + 2);
                secondColumnWidth =
                    Math.max(secondColumnWidth, Integer.toString(mostFrequentlyRequestedResources.get(key)).length());
            }
            formatter.format(getTitle("Запрашиваемые ресурсы"));
            if (formant == Format.ADOC) {
                formatter.format(getSeparator(firstColumnWidth, secondColumnWidth));
            }
            format = getFormat(firstColumnWidth, secondColumnWidth);
            formatter.format(format, "Ресурс", "Количество");
            if (formant == Format.MARKDOWN) {
                formatter.format(getSeparator(firstColumnWidth, secondColumnWidth));
            }
            for (String key : mostFrequentlyRequestedResources.keySet()) {
                formatter.format(format, "'" + key + "'", mostFrequentlyRequestedResources.get(key));
            }
            if (formant == Format.ADOC) {
                formatter.format(getSeparator(firstColumnWidth, secondColumnWidth));
            }

            // codes
            firstColumnWidth = "Код".length();
            secondColumnWidth = "Имя".length();
            int thirdColumnWidht = "Количество".length();
            for (Integer key : mostCommonResponseCodes.keySet()) {
                firstColumnWidth = Math.max(firstColumnWidth, Integer.toString(key).length());
                secondColumnWidth = Math.max(secondColumnWidth, NGINX_CODES.get(key).length());
                thirdColumnWidht =
                    Math.max(thirdColumnWidht, Integer.toString(mostCommonResponseCodes.get(key)).length());
            }
            format = getFormat(firstColumnWidth, secondColumnWidth, thirdColumnWidht);
            formatter.format(getTitle("Коды ответа"));
            if (formant == Format.ADOC) {
                formatter.format(getSeparator(firstColumnWidth, secondColumnWidth, thirdColumnWidht));
            }
            formatter.format(format, "Код", "Имя", "Количество");
            if (formant == Format.MARKDOWN) {
                formatter.format(getSeparator(firstColumnWidth, secondColumnWidth, thirdColumnWidht));
            }
            for (Integer key : mostCommonResponseCodes.keySet()) {
                formatter.format(format, key, NGINX_CODES.get(key), mostCommonResponseCodes.get(key));
            }
            if (formant == Format.ADOC) {
                formatter.format(getSeparator(firstColumnWidth, secondColumnWidth, thirdColumnWidht));
            }

            // command
            firstColumnWidth = "Команда".length();
            secondColumnWidth = "Количество".length();
            for (String key : mostCommonCommand.keySet()) {
                firstColumnWidth = Math.max(firstColumnWidth, key.length() + 2);
                secondColumnWidth =
                    Math.max(secondColumnWidth, Integer.toString(mostCommonCommand.get(key)).length());
            }
            formatter.format(getTitle("Частота встречи команд в запросах"));
            if (formant == Format.ADOC) {
                formatter.format(getSeparator(firstColumnWidth, secondColumnWidth));
            }
            format = getFormat(firstColumnWidth, secondColumnWidth);
            formatter.format(format, "Команда", "Количество");
            if (formant == Format.MARKDOWN) {
                formatter.format(getSeparator(firstColumnWidth, secondColumnWidth));
            }
            for (String key : mostCommonCommand.keySet()) {
                formatter.format(format, "'" + key + "'", mostCommonCommand.get(key));
            }
            if (formant == Format.ADOC) {
                formatter.format(getSeparator(firstColumnWidth, secondColumnWidth));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
