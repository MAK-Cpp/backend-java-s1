package edu.hw4.tasks.task3;

import edu.hw4.Animal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;
import static edu.hw4.Animal.Type.*;
import static edu.hw4.tasks.RandomAnimalGenerator.randomAnimal;

@DisplayName("Task 3")
class AnimalTypeCountTest {
    @ParameterizedTest
    @MethodSource
    void testAnimalTypeCount(final Collection<Animal> input, final Map<Animal.Type, Integer> output) {
        assertThat(AnimalTypeCount.count(input)).isEqualTo(output);
    }

    private static Animal animalByType(final Animal.Type type) {
        return randomAnimal(null, type, null, null, null, null, null);
    }

    private static Stream<Arguments> testAnimalTypeCount() {
        return Stream.of(
            Arguments.of(
                List.of(
                ),
                Map.of(
                )
            ),
            Arguments.of(
                List.of(
                    animalByType(FISH),
                    animalByType(DOG),
                    animalByType(BIRD),
                    animalByType(BIRD),
                    animalByType(DOG),
                    animalByType(DOG)
                ),
                Map.of(
                    FISH, 1,
                    DOG, 3,
                    BIRD, 2
                )
            ),
            Arguments.of(
                List.of(
                    animalByType(DOG),
                    animalByType(FISH),
                    animalByType(BIRD),
                    animalByType(DOG),
                    animalByType(BIRD),
                    animalByType(FISH),
                    animalByType(CAT),
                    animalByType(BIRD),
                    animalByType(SPIDER),
                    animalByType(FISH)
                ),
                Map.of(
                    SPIDER, 1,
                    FISH, 3,
                    CAT, 1,
                    DOG, 2,
                    BIRD, 3
                )
            )
        );
    }
}
