package edu.hw1.tasks;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task4Test {
    @ParameterizedTest
    @MethodSource
    @DisplayName("Починка строки, в которой S[i] <-> S[i + 1]")
    void testFixString(final String input, final String expected) {
        assertEquals(expected, Task4.fixString(input));
    }

    private static Stream<Arguments> testFixString() {
        return Stream.of(
            Arguments.of("123456", "214365"),
            Arguments.of("hTsii  s aimex dpus rtni.g", "This is a mixed up string."),
            Arguments.of("badce", "abcde"),
            Arguments.of("", ""),
            Arguments.of("A", "A"),
            Arguments.of("eHll,ow rodl!", "Hello, world!")
        );
    }
}
