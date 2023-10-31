package edu.hw4.tasks.task12;

import edu.hw4.Animal;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;
import static edu.hw4.Animal.Type.*;
import static edu.hw4.Animal.Sex.*;

class AnimalsWhoseWeightExceedsTheirHeightTest {
    private static Animal animalByWeightAndHeight(final int weight, final int height) {
        return new Animal("", CAT, F, 0, height, weight, false);
    }

    public static Stream<Arguments> testAnimalsWhoseWeightExceedsTheirHeight() {
        return Stream.of(
            Arguments.of(
                List.of(),
                0
            ),
            Arguments.of(
                List.of(
                    animalByWeightAndHeight(20, 30),
                    animalByWeightAndHeight(10, 15),
                    animalByWeightAndHeight(5, 5)
                ),
                0
            ),
            Arguments.of(
                List.of(
                    animalByWeightAndHeight(5, 1),
                    animalByWeightAndHeight(4, 2),
                    animalByWeightAndHeight(3, 3),
                    animalByWeightAndHeight(2, 4),
                    animalByWeightAndHeight(1, 5)
                ),
                2
            )

        );
    }

    @ParameterizedTest
    @MethodSource
    void testAnimalsWhoseWeightExceedsTheirHeight(final Collection<Animal> input, final Integer output) {
        assertThat(AnimalsWhoseWeightExceedsTheirHeight.count(input)).isEqualTo(output);
    }
}
