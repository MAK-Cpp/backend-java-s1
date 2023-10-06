package edu.hw1.tasks;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task2Test {
    @ParameterizedTest
    @MethodSource
    @DisplayName("Подсчет количества цифр в целом числе")
    void testCountDigits(final int input, final int expected) {
        assertEquals(expected, Task2.countDigits(input));
    }

    private static Stream<Arguments> testCountDigits() {
        return Stream.of(
            // tests from HW1.md
            Arguments.of(4666, 4),
            Arguments.of(544, 3),
            Arguments.of(0, 1),
            // my own tests
            Arguments.of(-1, 1),
            Arguments.of(Integer.MAX_VALUE, 10),
            Arguments.of(Integer.MIN_VALUE, 10)
        );
    }
}
