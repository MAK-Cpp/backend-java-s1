package edu.hw2.tasks.task1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExpressionsTest {
    @ParameterizedTest
    @MethodSource("expressions")
    @DisplayName("Любое выражение корректно вычисляется")
    void testExpressions(Expr expression, double expected) {
        assertEquals(expected, expression.evaluate(), 1e-5);

    }

    private static Stream<Arguments> expressions() {
        return Stream.of(
            Arguments.of(
                new Multiplication(
                    new Addition(new Constant(10), new Constant(333.3)),
                    new Multiplication(new Constant(3.1415), new Constant(37))
                ), 39903.64715
            ),
            Arguments.of(
                new Addition(
                    new Exponent(
                        new Multiplication(
                            new Addition(
                                new Constant(2),
                                new Constant(4)
                            ),
                            new Negate(new Constant(1))
                        ),
                        2
                    ),
                    new Constant(1)
                ), 37
            ),
            Arguments.of(
                new Exponent(
                    new Negate(
                        new Constant(3)
                    ), 3
                ), -27
            ),
            Arguments.of(
                new Addition(new Addition(new Addition(new Addition(
                    new Addition(new Constant(1), new Constant(2)),
                    new Constant(3)
                ), new Constant(4)), new Constant(5)), new Constant(6)),
                21
            ),
            Arguments.of(new Multiplication(new Constant(123.45), new Exponent(new Constant(123.45), -1)), 1)
        );
    }
}
