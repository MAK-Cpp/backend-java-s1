package edu.hw3.tasks.task4;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import static edu.hw3.tasks.task4.RomanNumerals.convertToRoman;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RomanNumeralsTest {
    @ParameterizedTest
    @MethodSource("correctIntegersToConvert")
    public void testCorrectRomanNumeralsConvert(final int number, final String result) {
        assertThat(convertToRoman(number)).isEqualTo(result);
    }

    @ParameterizedTest
    @MethodSource("incorrectIntegersToConvert")
    public void testIncorrectRomanNumeralsConvert(final int number) {
        final RomanNumeralsException exception = assertThrows(RomanNumeralsException.class, () -> convertToRoman(number));
        assertThat(exception.getMessage()).isEqualTo("Invalid number to convect to Roman");
    }

    private static Stream<Arguments> correctIntegersToConvert() {
        return Stream.of(
            Arguments.of(2, "II"),
            Arguments.of(12, "XII"),
            Arguments.of(16, "XVI"),
            Arguments.of(273, "CCLXXIII"),
            Arguments.of(3246, "MMMCCXLVI"),
            Arguments.of(204, "CCIV"),
            Arguments.of(1155, "MCLV"),
            Arguments.of(934, "CMXXXIV"),
            Arguments.of(131, "CXXXI"),
            Arguments.of(3443, "MMMCDXLIII"),
            Arguments.of(2787, "MMDCCLXXXVII"),
            Arguments.of(1811, "MDCCCXI"),
            Arguments.of(801, "DCCCI")
        );
    }

    private static IntStream incorrectIntegersToConvert() {
        return IntStream.of(4000, -100, -4000);
    }
}
