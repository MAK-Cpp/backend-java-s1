package edu.hw4.tasks.task7;

import edu.hw4.Animal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;
import static edu.hw4.tasks.RandomAnimalGenerator.randomAnimal;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Task 7")
class KOldestAnimalTest {
    private static Animal animalByAge(int age) {
        return randomAnimal(null, null, null, age, null, null, null);
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
                    animalByAge(8),
                    animalByAge(46),
                    animalByAge(23),
                    animalByAge(33)
                ),
                4,
                4
            )
        );
    }

    @ParameterizedTest
    @MethodSource
    void testKOldestAnimal(final Collection<Animal> input, final int k, final Integer outputIndex) {
        assertThat(KOldestAnimal.find(input, k)).isEqualTo(outputIndex == null ? null : input.stream().toList().get(outputIndex));
    }
}
