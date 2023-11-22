package edu.project3;

import edu.project3.analyzer.Flag;
import edu.project3.analyzer.LogAnalyzer;
import edu.project3.analyzer.LogReport;
import edu.table.Format;
import java.net.URI;
import java.net.URISyntaxException;

public final class Main {
    private Main() {
    }

    private static void parseValue(final LogAnalyzer analyzer, final Flag flag, final String value) {
        switch (flag) {
            case PATH -> {
                try {
                    URI uri = new URI(value);
                    String scheme = uri.getScheme();
                    if (scheme == null) {
                        analyzer.addRecord(value);
                    } else {
                        analyzer.addRecord(uri);
                    }
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            }
            case FROM -> analyzer.setFrom(value);
            case TO -> analyzer.setTo(value);
            case FORMAT -> analyzer.setFormat(Format.valueOf(value.toUpperCase()));
            case OUT -> analyzer.setOut(value);
            default -> throw new IllegalArgumentException("Unknown component: " + value);
        }
    }

    public static void main(String[] args) {
        Flag currentFlag = Flag.NULL;
        final LogAnalyzer analyzer = new LogAnalyzer();
        for (String arg : args) {
            if (arg.charAt(0) == '-' && arg.charAt(1) == '-') {
                currentFlag = Flag.valueOf(arg.substring(2).toUpperCase());
            } else {
                parseValue(analyzer, currentFlag, arg);
            }
        }
        LogReport report = analyzer.getReport();
        report.print("report");
    }
}
