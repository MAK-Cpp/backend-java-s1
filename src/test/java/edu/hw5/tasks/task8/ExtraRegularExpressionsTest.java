package edu.hw5.tasks.task8;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Task 8")
class ExtraRegularExpressionsTest {
    public static Stream<Arguments> testExtraRegularExpressions() {
        return Stream.of(
            Arguments.of("110", 0b1001000),
            Arguments.of("111", 0b1010100),
            Arguments.of("11", 0b0110100),
            Arguments.of("01010101010", 0b1111001),
            Arguments.of("10101011010100101000110101110110", 0b0111000),
            Arguments.of("10000000000000000000000000000000", 0b0101011),
            Arguments.of("00000000000000010000000000000000", 0b0001011),
            Arguments.of("00000000000000000000000000000001", 0b0001011),
            Arguments.of("00000000000000000000000000000000", 0b0001011),
            Arguments.of("01111111111111111111111111111111", 0b0001000),
            Arguments.of("11111111111111101111111111111111", 0b0101100),
            Arguments.of("11111111111111111111111111111110", 0b0101100),
            Arguments.of("11111111111111111111111121111110", 0),
            Arguments.of("1111111111a111111111111111111110", 0)
        );
    }

    @ParameterizedTest
    @MethodSource
    void testExtraRegularExpressions(final String input, final int outputs) {
        for (int i = ExtraRegularExpressions.values().length - 1, j = outputs; i >= 0; i--, j >>= 1) {
            assertThat(ExtraRegularExpressions.values()[i].find(input)).isEqualTo(((j & 1) == 1));
        }
    }
}
