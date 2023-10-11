package edu.hw2.tasks.task1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.DoubleStream;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConstantTest {
    @ParameterizedTest
    @MethodSource("values")
    @DisplayName("Constant корректно хранит в себе любое число в типе double")
    void testConstant(double input) {
        assertEquals(input, (new Constant(input)).evaluate());
    }

    private static DoubleStream values() {
        return DoubleStream.of(
            4.5,
            Math.sqrt(2),
            -100.12,
            12345,
            Math.log10(2048) / Math.log10(2),
            Double.MAX_VALUE,
            Double.MIN_VALUE,
            Double.MIN_NORMAL,
            Double.MAX_EXPONENT,
            Double.MIN_EXPONENT
        );
    }
}
