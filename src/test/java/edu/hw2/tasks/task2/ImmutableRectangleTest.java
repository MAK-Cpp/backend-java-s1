package edu.hw2.tasks.task2;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;

public class ImmutableRectangleTest {
    @ParameterizedTest
    @MethodSource("setRectangleWidth")
    void testImmutableRectangleSetWidth(int width, int height, int newWidth, int expected) {
        assertThat(new ImmutableRectangle(width, height).setWidth(newWidth).area()).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("setRectangleHeight")
    void testImmutableRectangleSetHeight(int width, int height, int newHeight, int expected) {
        assertThat(new ImmutableRectangle(width, height).setHeight(newHeight).area()).isEqualTo(expected);
    }

    private static Stream<Arguments> setRectangleWidth() {
        return Stream.of(
            Arguments.of(4, 5, 6, 30),
            Arguments.of(10, 10, 10, 100),
            Arguments.of(5, 2, 100, 200),
            Arguments.of(100, 200, 0, 0)
        );
    }

    private static Stream<Arguments> setRectangleHeight() {
        return Stream.of(
            Arguments.of(4, 5, 6, 24),
            Arguments.of(10, 10, 10, 100),
            Arguments.of(5, 2, 100, 500),
            Arguments.of(100, 200, 0, 0)
        );
    }
}
