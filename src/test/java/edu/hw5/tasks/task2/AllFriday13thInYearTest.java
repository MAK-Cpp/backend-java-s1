package edu.hw5.tasks.task2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Task 2.1")
class AllFriday13thInYearTest {
    public static Date createDate(int year, int month, int day) {
        return new GregorianCalendar(year, month, day).getTime();
    }

    public static Stream<Arguments> testAllFriday13thInYear() {
        return Stream.of(
            Arguments.of(
                1925,
                List.of(
                    createDate(1925, Calendar.FEBRUARY, 13),
                    createDate(1925, Calendar.MARCH, 13),
                    createDate(1925, Calendar.NOVEMBER, 13)
                )
            ),
            Arguments.of(
                2024,
                List.of(
                    createDate(2024, Calendar.SEPTEMBER, 13),
                    createDate(2024, Calendar.DECEMBER, 13)
                )
            )
        );
    }

    @ParameterizedTest
    @MethodSource
    void testAllFriday13thInYear(final int input, final Collection<Date> output) {
        assertThat(AllFriday13thInYear.find(input)).isEqualTo(output);
    }
}
