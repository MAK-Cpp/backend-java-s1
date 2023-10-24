package edu.hw2.tasks.task1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class NegateTest {
    @ParameterizedTest
    @MethodSource("correctData")
    @DisplayName("Negate корректно вычисляет выражение")
    void testNegate(Expr operand) {
        assertEquals(-operand.evaluate(), new Negate(operand).evaluate());
    }

    private static Stream<Expr> correctData() {
        return Stream.of(
            new Constant(13),
            new Constant(-89.99999),
            new Negate(new Constant(123.21))
        );
    }
}
