package edu.hw2.tasks.task1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExponentTest {
    @ParameterizedTest
    @MethodSource("correctData")
    @DisplayName("Exponent корректно вычисляется на корректных данных")
    void testExponent(Expr expression, double power, double expected) {
        assertEquals(expected, new Exponent(expression, power).evaluate());
    }

    private static Stream<Arguments> correctData() {
        return Stream.of(
            Arguments.of(new Constant(5), 2, 25),
            Arguments.of(new Constant(-3), -3, Math.pow(-27, -1)),
            Arguments.of(new Constant(1024), 0.1, 2)
        );
    }
}
