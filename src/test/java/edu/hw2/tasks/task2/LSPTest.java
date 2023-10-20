package edu.hw2.tasks.task2;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LSPTest {
    static Arguments[] rectangles() {
        return new Arguments[]{
            Arguments.of(new ImmutableRectangle()),
            Arguments.of(new ImmutableSquare())
        };
    }

    @ParameterizedTest
    @MethodSource("rectangles")
    void rectangleArea(ImmutableRectangle rect) {
        rect = rect.setWidth(20).setHeight(10);

        assertThat(rect.area()).isEqualTo(200.0);
    }
}
