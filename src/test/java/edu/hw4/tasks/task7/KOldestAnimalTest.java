package edu.hw4.tasks.task7;

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

@DisplayName("Task 7")
class KOldestAnimalTest {
    private static Animal animalByAge(int age) {
        return new Animal("", DOG, M, age, 0, 0, false);
    }

    public static Stream<Arguments> testKOldestAnimal() {
        return Stream.of(
            Arguments.of(
                List.of(),
                3,
                null
            ),
            Arguments.of(
                List.of(
                    animalByAge(5),
                    animalByAge(10),
                    animalByAge(3),
                    animalByAge(4)
                ),
                5,
                null
            ),
            Arguments.of(
                List.of(
                    animalByAge(100),
                    animalByAge(13),
                    animalByAge(23),
                    animalByAge(8),
                    animalByAge(46),
                    animalByAge(23),
                    animalByAge(33)
                ),
                4,
                animalByAge(23)
            )
        );
    }

    @ParameterizedTest
    @MethodSource
    void testKOldestAnimal(final Collection<Animal> input, final int k, final Animal output) {
        assertThat(KOldestAnimal.find(input, k)).isEqualTo(output);
    }
}
