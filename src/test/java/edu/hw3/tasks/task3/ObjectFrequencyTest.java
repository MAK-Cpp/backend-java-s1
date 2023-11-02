package edu.hw3.tasks.task3;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import static edu.hw3.tasks.task3.ObjectFrequency.freqDict;
import static org.assertj.core.api.Assertions.assertThat;

public class ObjectFrequencyTest {
    @ParameterizedTest
    @MethodSource("objectsAndFrequencies")
    public <T> void testObjectFrequency(final T[] objects, final HashMap<T, Integer> frequencies) {
        assertThat(freqDict(objects)).isEqualTo(frequencies);
    }

    private static Stream<Arguments> objectsAndFrequencies() {
        return Stream.of(
            Arguments.of(new String[]{"a", "bb", "a", "bb"}, new HashMap<>(Map.of("bb", 2, "a", 2))),
            Arguments.of(new String[]{"this", "and", "that", "and"}, new HashMap<>(Map.of("that", 1, "and", 2, "this", 1))),
            Arguments.of(new String[]{"код", "код", "код", "bug"}, new HashMap<>(Map.of("код", 3, "bug", 1))),
            Arguments.of(new Integer[]{1, 1, 2, 2}, new HashMap<>(Map.of(1, 2, 2, 2))),
            Arguments.of(new Object[]{}, new HashMap<>()),
            Arguments.of(new Boolean[]{true, false, false, false, true}, new HashMap<>(Map.of(true, 2, false, 3))),
            Arguments.of(
                new Object[]{"abc", 123, true, 'd', "abc", 345, false, false, 'a'},
                new HashMap<>(Map.of("abc", 2, 123, 1, true, 1, 'd', 1, 345, 1, false, 2, 'a', 1)))
        );
    }
}
