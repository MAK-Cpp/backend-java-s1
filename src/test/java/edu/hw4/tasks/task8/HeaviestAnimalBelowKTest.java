package edu.hw4.tasks.task8;

import edu.hw4.Animal;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;
import static edu.hw4.Animal.Type.*;
import static edu.hw4.Animal.Sex.*;

class HeaviestAnimalBelowKTest {
    private static Animal animalByHeightAndWeight(int height, int weight) {
        return new Animal("", DOG, M, 0, height, weight, false);
    }

    public static Stream<Arguments> testHeaviestAnimalBelowK() {
        return Stream.of(
            Arguments.of(
                List.of(),
                173,
                Optional.empty()
            ),
            Arguments.of(
                List.of(
                    animalByHeightAndWeight(30, 10),
                    animalByHeightAndWeight(20, 1222),
                    animalByHeightAndWeight(180, 60)
                ),
                20,
                Optional.empty()
            ),
            Arguments.of(
                List.of(
                    animalByHeightAndWeight(15, 17),
                    animalByHeightAndWeight(65, 85),
                    animalByHeightAndWeight(1470, 48),
                    animalByHeightAndWeight(580, 125),
                    animalByHeightAndWeight(60, 80),
                    animalByHeightAndWeight(2, 1)
                ),
                100,
                Optional.of(animalByHeightAndWeight(65, 85))
            )
        );
    }

    @ParameterizedTest
    @MethodSource
    void testHeaviestAnimalBelowK(final Collection<Animal> input, final int k, final Optional<Animal> output) {
        assertThat(HeaviestAnimalBelowK.find(input, k)).isEqualTo(output);
    }
}
