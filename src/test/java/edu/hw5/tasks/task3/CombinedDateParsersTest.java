package edu.hw5.tasks.task3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Task 3")
class CombinedDateParsersTest {
    public static Stream<Arguments> testCombinedDateParsers() {
        return Stream.of(
            Arguments.of(
                "2020-10-10",
                Optional.of(LocalDate.of(2020, 10, 10))
            ),
            Arguments.of(
                "2020-12-2",
                Optional.of(LocalDate.of(2020, 12, 2))
            ),
            Arguments.of(
                "1/3/1976",
                Optional.of(LocalDate.of(1976, 3, 1))
            ),
            Arguments.of(
                "10/12/20",
                Optional.of(LocalDate.of(20, 12, 10))
            ),
            Arguments.of(
                "tomorrow",
                Optional.of(LocalDate.now().plusDays(1))
            ),
            Arguments.of(
                "today",
                Optional.of(LocalDate.now())
            ),
            Arguments.of(
                "yesterday",
                Optional.of(LocalDate.now().minusDays(1))
            ),
            Arguments.of(
                "1 day ago",
                Optional.of(LocalDate.now().minusDays(1))
            ),
            Arguments.of(
                "2234 days ago",
                Optional.of(LocalDate.now().minusDays(2234))
            ),
            Arguments.of(
                "1 january, 1984",
                Optional.empty()
            ),
            Arguments.of(
                "111-111-111",
                Optional.empty()
            ),
            Arguments.of(
                "123/456/789",
                Optional.empty()
            ),
            Arguments.of(
                "3 days after",
                Optional.empty()
            ),
            Arguments.of(
                "1 day after",
                Optional.empty()
            )
        );
    }

    @ParameterizedTest
    @MethodSource
    void testCombinedDateParsers(final String input, final Optional<LocalDate> output) {
        assertThat(CombinedDateParsers.parseDate(input)).isEqualTo(output);
    }
}
