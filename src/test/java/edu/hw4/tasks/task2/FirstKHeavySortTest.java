package edu.hw4.tasks.task2;

import edu.hw4.Animal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;
import static edu.hw4.Animal.Type.*;
import static edu.hw4.Animal.Sex.*;

@DisplayName("Task 2")
class FirstKHeavySortTest {
    @ParameterizedTest
    @MethodSource
    void testFirstKHeavySort(Collection<Animal> input, int k, List<Animal> output) {
        assertThat(FirstKHeavySort.sort(input, k)).isEqualTo(output);
    }

    public static Stream<Arguments> testFirstKHeavySort() {
        return Stream.of(
            Arguments.of(
                List.of(),
                0,
                List.of()
            ),
            Arguments.of(
                List.of(
                    new Animal("Biber", DOG, M, 10, 38, 24, false),
                    new Animal("Dolik", CAT, F, 13, 10, 40, true),
                    new Animal("Bolik", FISH, F, 2, 1, 3, false),
                    new Animal("Diber", BIRD, M, 5, 30, 17, false)
                ),
                5,
                List.of(
                    new Animal("Dolik", CAT, F, 13, 10, 40, true),
                    new Animal("Biber", DOG, M, 10, 38, 24, false),
                    new Animal("Diber", BIRD, M, 5, 30, 17, false),
                    new Animal("Bolik", FISH, F, 2, 1, 3, false)
                )
            ),
            Arguments.of(
                List.of(
                    new Animal("A", CAT, M, 1, 3, 2, true),
                    new Animal("B", DOG, F, 5, 1, 3, true),
                    new Animal("C", FISH, M, 2, 5, 4, true),
                    new Animal("D", BIRD, F, 4, 2, 1, false),
                    new Animal("E", SPIDER, M, 3, 4, 5, true)
                ),
                3,
                List.of(
                    new Animal("E", SPIDER, M, 3, 4, 5, true),
                    new Animal("C", FISH, M, 2, 5, 4, true),
                    new Animal("B", DOG, F, 5, 1, 3, true)
                )
            )
        );
    }
}
