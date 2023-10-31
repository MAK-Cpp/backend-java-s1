package edu.hw4.tasks.task10;

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

class AgeDoesntMatchPawsAnimalsTest {
    private static Animal animalByTypeAndAge(final Animal.Type type, final int age) {
        return new Animal("", type, M, age, 0, 0, false);
    }

    public static Stream<Arguments> testAgeDoesntMatchPawsAnimals() {
        return Stream.of(
            Arguments.of(
                List.of(),
                List.of()
            ),
            Arguments.of(
                List.of(
                    animalByTypeAndAge(DOG, 4),
                    animalByTypeAndAge(CAT, 4),
                    animalByTypeAndAge(BIRD, 2),
                    animalByTypeAndAge(FISH, 0),
                    animalByTypeAndAge(SPIDER, 8)
                ),
                List.of()
            ),
            Arguments.of(
                List.of(
                    animalByTypeAndAge(DOG, 10),
                    animalByTypeAndAge(DOG, 3),
                    animalByTypeAndAge(DOG, 4),
                    animalByTypeAndAge(CAT, 13),
                    animalByTypeAndAge(CAT, 1),
                    animalByTypeAndAge(BIRD, 1),
                    animalByTypeAndAge(BIRD, 2),
                    animalByTypeAndAge(SPIDER, 1)
                ),
                List.of(
                    animalByTypeAndAge(DOG, 10),
                    animalByTypeAndAge(DOG, 3),
                    animalByTypeAndAge(CAT, 13),
                    animalByTypeAndAge(CAT, 1),
                    animalByTypeAndAge(BIRD, 1),
                    animalByTypeAndAge(SPIDER, 1)
                )
            )
        );
    }

    @ParameterizedTest
    @MethodSource
    void testAgeDoesntMatchPawsAnimals(final Collection<Animal> input, final List<Animal> output) {
        assertThat(AgeDoesntMatchPawsAnimals.find(input)).isEqualTo(output);
    }
}
