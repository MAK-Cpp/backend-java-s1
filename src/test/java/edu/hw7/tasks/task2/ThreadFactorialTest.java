package edu.hw7.tasks.task2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.math.BigInteger;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Task 2")
class ThreadFactorialTest {
    public static Stream<Arguments> testThreadFactorial() {
        return Stream.of(
            Arguments.of(5, BigInteger.valueOf(120)),
            Arguments.of(10, BigInteger.valueOf(3628800)),
            Arguments.of(20, BigInteger.valueOf(Long.parseLong("2432902008176640000")))
        );
    }

    @ParameterizedTest
    @MethodSource
    void testThreadFactorial(int input, BigInteger output) {
        assertThat(ThreadFactorial.calculate(input)).isEqualTo(output);
    }
}
