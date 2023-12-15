package edu.hw9.tasks.task1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Task 1")
class MultiThreadStatsCollectorTest {
    private static final int TEST_REPEAT_COUNT = 10_000;

    private static Entry<String, double[]> task(String name, double... values) {
        return Map.entry(name, values);
    }

    public static Stream<Arguments> testMultiThreadStatsCollector() {
        return Stream.of(
            Arguments.of(
                // list of threads
                List.of(
                    // list of tasks in one thread
                    List.of(
                        // task
                        task("GTA VI price", 13.5, 70.6, 1337.0, 7.0),
                        task("Duration of doing homework", 0.1, 0.0, 1.0, 0.5, 9.0, 0.3),
                        task("Salary of Python programmers", 0.0, 0.0, 0.1, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
                        task("GTA VI price", 99.99)
                    ),
                    List.of(
                        task("GTA VI price", 5.0, 6.0, 7.0),
                        task("Weight of my left big toe", 0.13, 0.14, 0.13, 0.13, 0.15, 0.14, 0.14, 0.13),
                        task("Duration of doing homework", 100.0, 50.0, 25.0, 12.5, 6.25, 3.125)
                    ),
                    List.of(
                        task("Weight of my left big toe", 8.231, 10.3, 1.0, 3.1415),
                        task("Weight of my left big toe", 0.0),
                        task("Salary of Python programmers", 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 0.1),
                        task("GTA VI price", 20.25, 27.41, 4.24, 45.66)
                    ),
                    List.of(
                        task("Salary of Python programmers", 10.0),
                        task("Duration of doing homework", 2.71, 12345e-3, 72.738),
                        task("Duration of doing homework", 0.59, 9.99, 7.23, 8.0),
                        task("Weight of my left big toe", 1.0, 1.0, 1.0, 1.0, 1.1, 1.1, 1.1, 1.0)
                    )
                ),
                // list of statistics
                List.of(
                    new Statistic("Salary of Python programmers", 14.6, 0.73, 10.0, 0.0),
                    new Statistic("GTA VI price", 1643.65, 136.97083333333333, 1337.0, 4.24),
                    new Statistic("Duration of doing homework", 321.378/*00000000004*/, 16.91463157894737, 100.0, 0.0),
                    new Statistic("Weight of my left big toe", 32.0625, 1.5267857142857142, 10.3, 0.0)
                )
            )
        );
    }

    @ParameterizedTest
    @MethodSource
    void testMultiThreadStatsCollector(List<List<Entry<String, double[]>>> inputs, List<Statistic> result) {
        // list of lists of entries = a lot of threads, each them have a lot of tasks.
        for (int test = 0; test < TEST_REPEAT_COUNT; test++) {
            try (ExecutorService threadPool =
                     Executors.newFixedThreadPool(Math.min(
                         inputs.size(),
                         Runtime.getRuntime().availableProcessors()
                     ))) {
                StatsCollector collector = new MultiThreadStatsCollector();
                AtomicInteger id = new AtomicInteger(0);

                var threadTasks = Stream.generate(() -> CompletableFuture.runAsync(() -> {
                    List<Entry<String, double[]>> tasks = inputs.get(id.getAndIncrement());
                    for (Entry<String, double[]> task : tasks) {
                        collector.push(task.getKey(), task.getValue());
                    }
                }, threadPool)).limit(inputs.size()).toArray(CompletableFuture[]::new);
                CompletableFuture.allOf(threadTasks).join();

                List<Statistic> output = collector.stats();
                assertThat(output).isEqualTo(result);
            }
        }
    }
}
