package edu.project3.analyzer;

import edu.table.Format;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.stream.Stream;

public class LogAnalyzer {
    private final ArrayList<LogRecord> records;
    private Instant from;
    private Instant to;
    private Format format;
    private final ArrayList<String> filenames;
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public LogAnalyzer() {
        this.records = new ArrayList<>();
        filenames = new ArrayList<>();
        format = Format.ADOC;
    }

    public void addRecord(final URI remoteLog) {
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpResponse<Stream<String>> response = client.send(
                HttpRequest.newBuilder().uri(remoteLog).GET().build(),
                HttpResponse.BodyHandlers.ofLines()
            );
            final String stringPath = remoteLog.getPath();
            filenames.add(stringPath.substring(stringPath.lastIndexOf('/') + 1));
            response.body().map(LogRecord::parse).forEach(records::add);
            response.body().close();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void addRecord(final String localLog) {
        final PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:**" + localLog);
        try {
            Files.walkFileTree(
                Path.of(System.getProperty("user.dir")),
                new SimpleFileVisitor<>() {
                    @Override
                    public FileVisitResult visitFile(
                        Path path,
                        BasicFileAttributes attrs
                    ) {
                        if (pathMatcher.matches(path)) {
                            try (Stream<String> lines = Files.lines(path)) {
                                lines.map(LogRecord::parse).forEach(records::add);
                                filenames.add(path.getFileName().toString());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult visitFileFailed(Path file, IOException exc) {
                        return FileVisitResult.CONTINUE;
                    }
                }
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setFrom(final String from) {
        try {
            this.from = SIMPLE_DATE_FORMAT.parse(from).toInstant();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public void setTo(final String to) {
        try {
            this.to = SIMPLE_DATE_FORMAT.parse(to).toInstant();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    public LogReport getReport() {
        LogReport report = new LogReport(
            format,
            String.join("; ", filenames),
            from == null ? "-" : from.toString(),
            to == null ? "-" : to.toString()
        );
        records.stream()
            .filter(x -> (from == null || x.timeLocal().isAfter(from.atOffset(x.timeLocal().getOffset())))
                && (to == null || x.timeLocal().isAfter(to.atOffset(x.timeLocal()
                    .getOffset()))))
            .forEach(x -> {
                report.addRequest();
                report.addSize(x.bodyBytesSent());
                report.addCode(x.status());
                report.parseRequest(x.request());
            });
        return report;
    }
}
