package edu.hw1.tasks;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task7Test {
    @ParameterizedTest
    @MethodSource("rightRotateArgs")
    @DisplayName("Правый бинарный циклический сдвиг")
    void testRightRotate(final int n, final int shift, final int expected) {
        assertEquals(expected, Task7.rotateRight(n, shift));
    }

    @ParameterizedTest
    @MethodSource("leftRotateArgs")
    @DisplayName("Левый бинарный циклический сдвиг")
    void testLeftRotate(final int n, final int shift, final int expected) {
        assertEquals(expected, Task7.rotateLeft(n, shift));
    }

    private static Stream<Arguments> rightRotateArgs() {
        return Stream.of(
            Arguments.of(8, 1, 4),
            Arguments.of(Integer.MAX_VALUE, 100, Integer.MAX_VALUE),
            Arguments.of(83123450, 12609, 83123450),
            Arguments.of(1457173, 788, 1070791)
        );
    }

    private static Stream<Arguments> leftRotateArgs() {
        return Stream.of(
            Arguments.of(16, 1, 1),
            Arguments.of(17, 2, 6),
            Arguments.of(17, 617, 6),
            Arguments.of(Integer.MAX_VALUE, 100, Integer.MAX_VALUE)
        );
    }
}
