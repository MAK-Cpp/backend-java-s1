package edu.hw4.tasks.task1;

import edu.hw4.Animal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;
import static edu.hw4.Animal.Type.*;
import static edu.hw4.Animal.Sex.*;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Task 1")
class HeightSortTest {
    @ParameterizedTest
    @MethodSource
    void testHeightSort(final Collection<Animal> input, final List<Animal> output) {
        assertThat(HeightSort.sort(input)).isEqualTo(output);
    }

    private static Stream<Arguments> testHeightSort() {
        return Stream.of(
            Arguments.of(
                List.of(),
                List.of()
            ),
            Arguments.of(
                List.of(
                    new Animal("bobik", DOG, M, 8, 40, 30, false),
                    new Animal("EMU", BIRD, F, 8, 220, 100, true),
                    new Animal("Man", SPIDER, M, 8, 3, 1, false)
                ),
                List.of(
                    new Animal("Man", SPIDER, M, 8, 3, 1, false),
                    new Animal("bobik", DOG, M, 8, 40, 30, false),
                    new Animal("EMU", BIRD, F, 8, 220, 100, true)
                )
            ),
            Arguments.of(
                new ArrayList<>(List.of(
                    new Animal("VOBLA", FISH, M, 1, 20, 1, false),
                    new Animal("HOBLA", FISH, M, 1, 5, 1, false),
                    new Animal("SHOBLA", FISH, M, 1, 1, 1, false),
                    new Animal("YOBLA", FISH, M, 1, 100, 1, false),
                    new Animal("KOBLA", FISH, M, 1, 30, 1, false)
                )),
                List.of(
                    new Animal("SHOBLA", FISH, M, 1, 1, 1, false),
                    new Animal("HOBLA", FISH, M, 1, 5, 1, false),
                    new Animal("VOBLA", FISH, M, 1, 20, 1, false),
                    new Animal("KOBLA", FISH, M, 1, 30, 1, false),
                    new Animal("YOBLA", FISH, M, 1, 100, 1, false)
                )
            )
        );
    }
}
