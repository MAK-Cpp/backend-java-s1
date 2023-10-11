package edu.hw2.tasks.task1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MultiplicationTest {
    @ParameterizedTest
    @MethodSource("correctData")
    @DisplayName("Multiplication корректно вычисляется на корректных данных")
    void testCorrectMultiplication(Expr a, Expr b, double expected) {
        assertEquals(expected, (new Multiplication(a, b)).evaluate(), 1e-5);
    }

    private static Stream<Arguments> correctData() {
        return Stream.of(
            Arguments.of(new Constant(5), new Constant(-3), -15),
            Arguments.of(new Constant(21.3), new Constant(88.9), 1893.5700000000002),
            Arguments.of(new Constant(0), new Constant(-100.12), 0),
            Arguments.of(new Constant(Double.MAX_VALUE), new Constant(Double.MIN_VALUE), 8.881784197001251E-16)
        );
    }
}
