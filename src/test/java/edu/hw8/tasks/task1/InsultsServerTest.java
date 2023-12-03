package edu.hw8.tasks.task1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.io.IOException;
import java.net.InetAddress;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Task 1")
class InsultsServerTest {
    public static Stream<Arguments> testInsultsServer() {
        return Stream.of(
            Arguments.of(
                List.of(
                    List.of(
                        Map.entry("a", "a"),
                        Map.entry("b", "b"),
                        Map.entry("c", "c")
                    ),
                    List.of(
                        Map.entry("личности", "Не переходи на личности там, где их нет")
                    ),
                    List.of(
                        Map.entry("оскорбления", "Если твои противники перешли на личные оскорбления, будь уверена — твоя победа не за горами")
                    ),
                    List.of(
                        Map.entry("глупый", "А я тебе говорил, что ты глупый? Так вот, я забираю свои слова обратно... Ты просто бог идиотизма.")
                    ),
                    List.of(
                        Map.entry("интеллект", "Чем ниже интеллект, тем громче оскорбления")
                    )
                )
            )
        );
    }

    @ParameterizedTest
    @MethodSource
    void testInsultsServer(List<List<Map.Entry<String, String>>> requests) {
        InsultsServer server = new InsultsServer();
        CountDownLatch latch = new CountDownLatch(requests.size());
        ExecutorService executorService = Executors.newFixedThreadPool(7);
        executorService.submit(server::start);
        executorService.submit(() -> {
            try {
                latch.await();
                server.stop();
            } catch (InterruptedException | IOException e) {
                throw new RuntimeException(e);
            }
        });
        AtomicInteger index = new AtomicInteger(0);
        var tasks = Stream.generate(() -> CompletableFuture.runAsync(
            () -> {
                List<Map.Entry<String, String>> request = requests.get(index.getAndIncrement());
                try (InsultClient client = new InsultClient(InetAddress.getLocalHost(), 49100)) {
                    for (Map.Entry<String, String> r : request) {
                        String result = client.get(r.getKey());
                        if (r.getValue() != null) {
                            assertThat(result).isEqualTo(r.getValue());
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } finally {
                    latch.countDown();
                }
            }, executorService)).limit(Math.min(5, requests.size())).toArray(CompletableFuture[]::new);
        CompletableFuture.allOf(tasks).join();
        executorService.shutdown();
    }
}
