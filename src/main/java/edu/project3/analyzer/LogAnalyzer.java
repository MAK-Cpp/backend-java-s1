package edu.project3.analyzer;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.stream.Stream;

public class LogAnalyzer {
    private final ArrayList<LogRecord> records;
    private OffsetDateTime from;
    private OffsetDateTime to;
    private Format format;
    private final ArrayList<String> filenames;

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

    public void addRecord(final Path localLog) {
        try (Stream<String> lines = Files.lines(localLog)) {
            lines.map(LogRecord::parse).forEach(records::add);
            filenames.add(localLog.getFileName().toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setFrom(final String from) {
        this.from = OffsetDateTime.parse(from);
    }

    public void setTo(final String to) {
        this.to = OffsetDateTime.parse(to);
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
            .filter(x -> (from == null || x.timeLocal().isAfter(from)) && (to == null || x.timeLocal().isAfter(to)))
            .forEach(x -> {
                report.addRequest();
                report.addSize(x.bodyBytesSent());
                report.addCode(x.status());
                report.parseRequest(x.request());
            });
        return report;
    }
}
