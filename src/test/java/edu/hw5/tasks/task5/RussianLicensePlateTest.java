package edu.hw5.tasks.task5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Task 5")
class RussianLicensePlateTest {
    public static Stream<Arguments> testRussianLicensePlate() {
        return Stream.of(
            Arguments.of("А123ВЕ777", true),
            Arguments.of("О777ОО177", true),
            Arguments.of("123АВЕ777",  false),
            Arguments.of("А123ВГ77", false),
            Arguments.of("А123ВЕ7777", false),
            Arguments.of("В666АД074", true),
            Arguments.of("Л000ОХ333", true)
        );
    }

    @ParameterizedTest
    @MethodSource
    void testRussianLicensePlate(final String input, final boolean output) {
        assertThat(RussianLicensePlate.check(input)).isEqualTo(output);
    }
}
