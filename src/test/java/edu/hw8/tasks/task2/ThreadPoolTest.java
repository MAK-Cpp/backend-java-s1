package edu.hw8.tasks.task2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Task 2")
class ThreadPoolTest {
    private static final int REPEAT_COUNT = 10;

    public static Stream<Arguments> testFixedThreadPool() {
        return Stream.of(
            Arguments.of(
                3,
                List.of(
                    Map.entry(5, 5L),
                    Map.entry(10, 55L),
                    Map.entry(15, 610L),
                    Map.entry(20, 6765L),
                    Map.entry(25, 75025L),
                    Map.entry(30, 832040L),
                    Map.entry(35, 9227465L),
                    Map.entry(40, 102334155L),
                    Map.entry(45, 1134903170L),
                    Map.entry(50, 12586269025L)
                )
            )
        );
    }

    private static void testFibonacciFunc(Map.Entry<Integer, Long> io) {
        List<Long> array = new ArrayList<>(io.getKey() + 1);
        array.add(0L);
        array.add(1, 1L);
        for (int j = 2; j <= io.getKey(); j++) {
            array.add(array.get(j - 1) + array.get(j - 2));
        }
        assertThat(array.get(io.getKey())).isEqualTo(io.getValue());
    }

    @ParameterizedTest
    @MethodSource
    void testFixedThreadPool(int threads, List<Map.Entry<Integer, Long>> inputsAndOutputs) {
        for (int testCount = 0; testCount < REPEAT_COUNT; testCount++) {
            final AtomicInteger countSolvedTasks = new AtomicInteger(0);
            try (FixedThreadPool threadPool = FixedThreadPool.create(threads)) {
                for (Map.Entry<Integer, Long> inputsAndOutput : inputsAndOutputs) {
                    threadPool.execute(() -> {
                        ThreadPoolTest.testFibonacciFunc(inputsAndOutput);
                        countSolvedTasks.incrementAndGet();
                    });
                }
                threadPool.start();
            }
            assertThat(countSolvedTasks.get()).isEqualTo(inputsAndOutputs.size());
        }
    }
}
