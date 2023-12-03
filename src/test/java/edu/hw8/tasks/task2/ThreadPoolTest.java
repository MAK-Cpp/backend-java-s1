package edu.hw8.tasks.task2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Task 2")
class ThreadPoolTest {
    public static Stream<Arguments> testFixedThreadPool() {
        return Stream.of(
            Arguments.of(List.of(
                Map.entry(6, 8),
                Map.entry(1, 1),
                Map.entry(10, 55),
                Map.entry(50, 7778742049L)
            ))
        );
    }

    @ParameterizedTest
    @MethodSource
    void testFixedThreadPool(List<Map.Entry<Long, Long>> inputsAndOutputs) throws Exception {
        try
        (FixedThreadPool threadPool = FixedThreadPool.create(inputsAndOutputs.size())) {
            AtomicInteger index = new AtomicInteger(0);
            for (int i = 0; i < inputsAndOutputs.size(); i++) {
                threadPool.execute(() -> {
                    Map.Entry<Long, Long> io = inputsAndOutputs.get(index.getAndIncrement());
                    if (io.getKey() < 2) {
                        assertThat(io.getValue()).isEqualTo(io.getKey());
                        return;
                    }
                    long[] array = new long[(int) (io.getKey() + 1)];
                    array[0] = 0;
                    array[1] = 1;
                    for (int j = 2; j < io.getKey(); j++) {
                        array[j] = array[j - 1] + array[j - 2];
                    }
                    assertThat(array[Math.toIntExact(io.getKey())]).isEqualTo(io.getValue());
                });
            }
            threadPool.start();
        }

    }
}
