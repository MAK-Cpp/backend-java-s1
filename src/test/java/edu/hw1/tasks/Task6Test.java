package edu.hw1.tasks;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task6Test {
    @ParameterizedTest
    @MethodSource
    @DisplayName("Функция Капрекара меньше чем за 7 шагов преобразовывает 4-х значное число (кроме 1000) в 6174")
    void testCountK(final int input, final int expected) {
        assertEquals(expected, Task6.countK(input));
    }

    private static Stream<Arguments> testCountK() {
        return Stream.of(
            Arguments.of(3524, 3),
            Arguments.of(6621, 5),
            Arguments.of(6554, 4),
            Arguments.of(1234, 3),
            Arguments.of(3412, 3),
            Arguments.of(1100, 4),
            Arguments.of(7641, 1),
            Arguments.of(2005, 7)
        );
    }
}
