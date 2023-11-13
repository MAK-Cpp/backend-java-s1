package edu.hw5.tasks.task4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Task 4")
class PasswordSecurityTest {
    public static Stream<String> testTruePasswordSecurity() {
        return Stream.of("@bc", "abc!", "uwu~~~!", "i ****ed everything!");
    }

    public static Stream<String> testFalsePasswordSecurity() {
        return Stream.of("abc", "1 + 2 = 3", "(. ) ( .)");
    }

    @ParameterizedTest
    @MethodSource
    void testTruePasswordSecurity(final String input) {
        assertTrue(PasswordSecurity.check(input));
    }

    @ParameterizedTest
    @MethodSource
    void testFalsePasswordSecurity(final String input) {
        assertFalse(PasswordSecurity.check(input));
    }
}
