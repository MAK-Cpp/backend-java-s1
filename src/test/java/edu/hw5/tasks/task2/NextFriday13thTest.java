package edu.hw5.tasks.task2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;
import static edu.hw5.tasks.task2.AllFriday13thInYearTest.createDate;

@DisplayName("Task 2.2")
class NextFriday13thTest {
    public static Stream<Arguments> testNextFriday13th() {
        return Stream.of(
            Arguments.of(
                createDate(2023, Calendar.SEPTEMBER, 26),
                createDate(2023, Calendar.OCTOBER, 13)
            ),
            Arguments.of(
                createDate(2021, Calendar.SEPTEMBER, 7),
                createDate(2022, Calendar.MAY, 13)
            ),
            Arguments.of(
                createDate(1992, Calendar.NOVEMBER, 14),
                createDate(1993, Calendar.AUGUST, 13)
            )
        );
    }

    @ParameterizedTest
    @MethodSource
    void testNextFriday13th(final Date input, final Date output) {
        assertThat(NextFriday13th.find(input)).isEqualTo(output);
    }
}
