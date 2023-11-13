package edu.hw5.tasks.task4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Task 4")
class PasswordSecurityTest {
    public static Stream<Arguments> testPasswordSecurity() { // ~ ! @ # $ % ^ & * |
        return Stream.of(
            Arguments.of(
                "abc",
                false
            ),
            Arguments.of(
                "@bc",
                true
            ),
            Arguments.of(
                "abc!",
                true
            ),
            Arguments.of(
                "uwu~~~!",
                true
            ),
            Arguments.of(
                "i ****ed everything!",
                true
            ),
            Arguments.of(
                "1 + 2 = 3",
                false
            ),
            Arguments.of(
                "(. ) ( .)",
                false
            )
        );
    }

    @ParameterizedTest
    @MethodSource
    void testPasswordSecurity(final String input, final boolean output) {
        assertThat(PasswordSecurity.check(input)).isEqualTo(output);
    }
}
