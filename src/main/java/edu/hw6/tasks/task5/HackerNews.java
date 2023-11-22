package edu.hw6.tasks.task5;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class HackerNews {
    private static final int SECONDS_TIMEOUT = 10;
    private static final Duration DURATION_TIMEOUT = Duration.of(SECONDS_TIMEOUT, ChronoUnit.SECONDS);
    private static final HttpRequest TOP_STORIES_REQUEST;
    private static final Pattern TITLE_PATTERN = Pattern.compile("\"title\":\"([^\"]*)\"");

    static {
        try {
            TOP_STORIES_REQUEST = HttpRequest.newBuilder()
                .uri(new URI("https://hacker-news.firebaseio.com/v0/topstories.json"))
                .GET()
                .timeout(DURATION_TIMEOUT)
                .build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private HackerNews() {
    }

    public static long[] hackerNewsTopStories() {
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpResponse<String> response = client.send(TOP_STORIES_REQUEST, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();
            String[] splitResponseBody = responseBody.substring(1, responseBody.length() - 1).split(",");
            return Arrays.stream(splitResponseBody).mapToLong(Long::parseLong).toArray();
        } catch (Exception e) {
            return new long[0];
        }
    }

    public static String news(final long id) {
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://hacker-news.firebaseio.com/v0/item/" + id + ".json"))
                .GET()
                .timeout(DURATION_TIMEOUT)
                .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            final Matcher matcher = TITLE_PATTERN.matcher(response.body());
            if (matcher.find()) {
                return matcher.group(1);
            }
            return "-";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
