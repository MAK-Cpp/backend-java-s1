package edu.project3.analyzer;

import edu.prettyTable.Format;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Stream;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

public class LogAnalyzer {
    private final ArrayList<LogRecord> records;
    private LocalDate from;
    private LocalDate to;
    private Format format;
    private Path out;
    private final ArrayList<String> filenames;
    private static final String USER_DIR = System.getProperty("user.dir");

    public LogAnalyzer() {
        this.records = new ArrayList<>();
        this.filenames = new ArrayList<>();
        this.format = Format.MARKDOWN;
        this.out = Path.of(USER_DIR);
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
                Path.of(USER_DIR),
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
        this.from = LocalDate.parse(from, ISO_LOCAL_DATE);
    }

    public void setTo(final String to) {
        this.to = LocalDate.parse(to, ISO_LOCAL_DATE);
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    public void setOut(final String out) {
        this.out = Path.of(out).toAbsolutePath();
    }

    public LogReport getReport() {
        filenames.sort(String::compareTo);
        LogReport report = new LogReport(
            format,
            String.join("; ", filenames),
            from == null ? "-" : from.toString(),
            to == null ? "-" : to.toString(),
            out
        );
        records.stream()
            .filter(x -> (from == null || x.timeLocal().toLocalDate().isAfter(from))
                && (to == null || x.timeLocal().toLocalDate().isBefore(to)))
            .forEach(x -> {
                report.addRequest();
                report.addSize(x.bodyBytesSent());
                report.addCode(x.status());
                report.parseRequest(x.request());
                report.addHttpUserAgent(x.httpUserAgent());
            });
        return report;
    }
}
