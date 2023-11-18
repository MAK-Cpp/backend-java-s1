package edu.hw5.tasks.task1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;
import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;
import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOutNormalized;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Task 1")
class ComputerClubAnalyticTest {
    public static Stream<Arguments> testComputerClubAnalytic() {
        return Stream.of(
            Arguments.of(
                List.of(
                    "2022-03-12, 20:20 - 2022-03-12, 23:50",
                    "2022-04-01, 21:30 - 2022-04-02, 01:20"
                ),
                "03h 40m\n"
            ),
            Arguments.of(
                List.of(),
                "00h 00m\n"
            ),
            Arguments.of(
                List.of(
                    "2021-03-12, 00:00 - 2021-03-13, 00:00",
                    "2007-11-29, 11:30 - 2007-11-29, 18:32",
                    "2020-07-21, 03:09 - 2020-07-21, 23:10",
                    "2023-12-31, 21:30 - 2024-01-01, 02:45"
                ),
                "14h 04m\n"
            )
        );
    }

    @ParameterizedTest
    @MethodSource
    void testComputerClubAnalytic(final Collection<String> input, final String output) throws Exception {
        String result = tapSystemOut(() -> ComputerClubAnalytic.calculate(input));
        assertThat(result).isEqualTo(output);
    }
}
