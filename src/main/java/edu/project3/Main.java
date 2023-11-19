package edu.project3;

import edu.project3.analyzer.Flags;
import edu.project3.analyzer.Format;
import edu.project3.analyzer.LogAnalyzer;
import edu.project3.analyzer.LogReport;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        Flags currentFlag = Flags.NULL;
        LogAnalyzer analyzer = new LogAnalyzer();
        for (String arg : args) {
            if (arg.charAt(0) == '-' && arg.charAt(1) == '-') {
                currentFlag = switch (arg) {
                    case "--path" -> Flags.PATH;
                    case "--from" -> Flags.FROM;
                    case "--to" -> Flags.TO;
                    case "--format" -> Flags.FORMAT;
                    default -> throw new IllegalArgumentException("Unknown flag: " + arg);
                };
            } else {
                switch (currentFlag) {
                    case PATH -> {
                        try {
                            analyzer.addRecord(new URI(arg));
                        } catch (URISyntaxException e) {
                            analyzer.addRecord(Path.of(arg));
                        }
                    }
                    case FROM -> analyzer.setFrom(arg);
                    case TO -> analyzer.setTo(arg);
                    case FORMAT -> analyzer.setFormat(
                        switch (arg) {
                            case "markdown" -> Format.MARKDOWN;
                            case "adoc" -> Format.ADOC;
                            default -> throw new IllegalArgumentException("Unknown format file: " + arg);
                        });
                    default -> throw new IllegalArgumentException("Unknown component: " + arg);
                }
            }
        }
        LogReport report = analyzer.getReport();
        report.print(Path.of(System.getProperty("user.dir")), "report");
    }
}
