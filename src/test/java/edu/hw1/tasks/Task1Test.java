package edu.hw1.tasks;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task1Test {

    @ParameterizedTest
    @MethodSource("validArgs")
    @DisplayName("Перевод времени формата mm:ss в секунды, корректные входные данные")
    void testValidMinutesToSeconds(final String input, final int expectedResult) {
        assertEquals(expectedResult, Task1.minutesToSeconds(input));
    }

    @ParameterizedTest
    @MethodSource("invalidArgs")
    @DisplayName("Перевод времени формата mm:ss в секунды, некорректные входные данные")
    void testInvalidMinutesToSeconds(final String input) {
        assertEquals(-1, Task1.minutesToSeconds(input));
    }

    private static Stream<Arguments> validArgs() {
        return Stream.of(
            Arguments.of("13:56", 836),
            Arguments.of("01:00", 60),
            Arguments.of("95:59", 5759),
            Arguments.of("0:0", 0),
            Arguments.of("1:23", 83)
        );
    }

    private static Stream<String> invalidArgs() {
        return Stream.of("10:60", "-1:20", "20:-1", "", ":12", "31:", "123", "1:2:3", "14:88", "Hello, world! :)");
    }
}
