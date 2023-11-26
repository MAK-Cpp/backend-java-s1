package edu.hw7.tasks.task1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Task 1")
class ThreadIncrementsTest {
    public static Stream<Arguments> testThreadIncrements() {
        return Stream.of(
            Arguments.of(
                List.of(Map.entry(5, true)),
                50
            ),
            Arguments.of(
                List.of(
                    Map.entry(30, false)
                ),
                -300
            ),
            Arguments.of(
                List.of(
                    Map.entry(10, true),
                    Map.entry(8, false)
                ),
                20
            ),
            Arguments.of(
                List.of(
                    Map.entry(10_000, true),
                    Map.entry(90_000, false),
                    Map.entry(20_000, true),
                    Map.entry(80_000, false),
                    Map.entry(30_000, true),
                    Map.entry(70_000, false),
                    Map.entry(40_000, true),
                    Map.entry(60_000, false),
                    Map.entry(50_000, true),
                    Map.entry(50_000, false),
                    Map.entry(60_000, true),
                    Map.entry(40_000, false),
                    Map.entry(70_000, true),
                    Map.entry(30_000, false),
                    Map.entry(80_000, true),
                    Map.entry(20_000, false),
                    Map.entry(90_000, true),
                    Map.entry(10_000, false)
                ),
                0
            )
        );
    }

    @ParameterizedTest
    @MethodSource
    void testThreadIncrements(List<Map.Entry<Integer, Boolean>> inputs, int output) {
        ThreadIncrements.resetCounter();
        try (ExecutorService threadPool = Executors.newFixedThreadPool(inputs.size())) {
            AtomicInteger iterator = new AtomicInteger(0);
            CompletableFuture[] tasks = Stream.generate(() -> CompletableFuture.runAsync(() -> {
                Map.Entry<Integer, Boolean> input = inputs.get(iterator.getAndIncrement());
                ThreadIncrements.run(input.getKey(), input.getValue());
            }, threadPool)).limit(inputs.size()).toArray(CompletableFuture[]::new);
            CompletableFuture.allOf(tasks).join();
            threadPool.shutdown();
            assertThat(ThreadIncrements.getCounter()).isEqualTo(output);
        }
    }
}
