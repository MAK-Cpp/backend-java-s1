package edu.hw5.tasks.task7;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Task 7")
class BaseRegularExpressionsTest {
    public static Stream<Arguments> testBaseRegularExpressions() {
        return Stream.of(
            Arguments.of("010", 0b111),
            Arguments.of("1010", 0b000),
            Arguments.of("121", 0b000),
            Arguments.of("1", 0b011),
            Arguments.of("0", 0b011),
            Arguments.of("100", 0b101),
            Arguments.of("101", 0b011),
            Arguments.of("01", 0b001),
            Arguments.of("00", 0b011),
            Arguments.of("11", 0b011),
            Arguments.of("10", 0b001),
            Arguments.of("", 0b000),
            Arguments.of("01011100111110101110011010101010001010001010101000111101010020", 0b000),
            Arguments.of("0101110011111010111001101010101000101000101010100011110101000", 0b110),
            Arguments.of("0111110011111010111001101010101000101000101010100011110101000", 0b010),
            Arguments.of("1101110011111010111001101010101000101000101010100011110101000", 0b100)
        );
    }

    @ParameterizedTest
    @MethodSource
    void testBaseRegularExpressions(final String input, final int outputs) {
        for (int i = BaseRegularExpressions.values().length - 1, j = outputs; i >= 0; i--, j >>= 1) {
            assertThat(BaseRegularExpressions.values()[i].find(input)).isEqualTo(((j & 1) == 1));
        }
    }
}
