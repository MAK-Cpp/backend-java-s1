package edu.hw2.tasks.task2;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;

public class ImmutableSquareTest {
    @ParameterizedTest
    @MethodSource("setSquareSide")
    void testImmutableSquareSetWidth(int size, int newWidth, int expectedArea) {
        assertThat(new ImmutableSquare(size).setWidth(newWidth).area()).isEqualTo(expectedArea);
    }

    @ParameterizedTest
    @MethodSource("setSquareSide")
    void testImmutableSquareSetHeight(int size, int newHeight, int expectedArea) {
        assertThat(new ImmutableSquare(size).setHeight(newHeight).area()).isEqualTo(expectedArea);
    }

    private static Stream<Arguments> setSquareSide() {
        return Stream.of(
            Arguments.of(3, 4, 12),
            Arguments.of(5, 2, 10),
            Arguments.of(10, 10, 100),
            Arguments.of(1, 98, 98)
        );
    }

}
