package edu.hw5.tasks.task5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Task 5")
class RussianLicensePlateTest {
    public static Stream<String> testTrueRussianLicensePlate() {
        return Stream.of("А123ВЕ777", "О777ОО177", "В666АД074", "Л000ОХ333");
    }

    public static Stream<String> testFalseRussianLicensePlate() {
        return Stream.of("123АВЕ777", "А123ВГ77", "А123ВЕ7777");
    }

    @ParameterizedTest
    @MethodSource
    void testTrueRussianLicensePlate(final String input) {
        assertTrue(RussianLicensePlate.check(input));
    }

    @ParameterizedTest
    @MethodSource
    void testFalseRussianLicensePlate(final String input) {
        assertFalse(RussianLicensePlate.check(input));
    }
}
