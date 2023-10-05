package edu.hw1.tasks;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Task3Test {
    @ParameterizedTest
    @MethodSource("nestableArgs")
    @DisplayName("Массив A можно вложить в массив B")
    void testNestable(final int[] a, final int[] b) {
        assertTrue(Task3.isNestable(a, b));
    }

    @ParameterizedTest
    @MethodSource("notNestableArgs")
    @DisplayName("Массив A нельзя вложить в массив B")
    void testNotNestable(final int[] a, final int[] b) {
        assertFalse(Task3.isNestable(a, b));
    }

    private static Stream<Arguments> nestableArgs() {
        return Stream.of(
            Arguments.of(new int[] {1, 2, 3, 4}, new int[] {0, 6}),
            Arguments.of(new int[] {3, 1}, new int[] {4, 0}),
            Arguments.of(new int[] {-10, -20, -30}, new int[] {-100, -1, 0, 1, 100})
        );
    }

    private static Stream<Arguments> notNestableArgs() {
        return Stream.of(
            Arguments.of(new int[] {9, 9, 8}, new int[] {8, 9}),
            Arguments.of(new int[] {1, 2, 3, 4}, new int[] {2, 3}),
            Arguments.of(new int[] {}, new int[] {1, 2, 3}),
            Arguments.of(new int[] {1, 2, 3}, new int[] {}),
            Arguments.of(new int[] {}, new int[] {})
        );
    }
}
