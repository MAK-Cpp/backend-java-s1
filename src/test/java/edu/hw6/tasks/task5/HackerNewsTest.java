package edu.hw6.tasks.task5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static edu.hw6.tasks.task5.HackerNews.hackerNewsTopStories;
import static edu.hw6.tasks.task5.HackerNews.news;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Task 5")
class HackerNewsTest {
    @Test
    void testHackerNewsTopStories() {
        try (HttpClient client = HttpClient.newHttpClient()) {
            final String result = client.send(HttpRequest.newBuilder()
                .uri(new URI("https://hacker-news.firebaseio.com/v0/topstories.json"))
                .GET()
                .build(), HttpResponse.BodyHandlers.ofString()).body();
            assertThat(
                "[" + Arrays.stream(hackerNewsTopStories()).mapToObj(Long::toString).collect(Collectors.joining(",")) +
                    "]").isEqualTo(result);
        } catch (IOException | URISyntaxException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static Stream<Arguments> testNews() {
        return Stream.of(
            Arguments.of(37570037, "JDK 21 Release Notes"),
            Arguments.of(1, "Y Combinator"),
            Arguments.of(100, "SpikeSource, CA-based startup, becomes Ubuntu commercial support provider for US"),
            Arguments.of(38304485, "280M e-bikes and mopeds are cutting demand for oil far more than electric cars"),
            Arguments.of(38284455, "Tenacity 1.3.3 – Audacity Fork"),
            Arguments.of(12345, "Distributed file storage: MogileFS"),
            Arguments.of(999999, "Hacker News Item 999,999")
        );
    }

    @ParameterizedTest
    @MethodSource
    void testNews(final int input, final String output) {
        assertThat(news(input)).isEqualTo(output);
    }
}
