package edu.hw4.tasks.task15;

import edu.hw4.Animal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import static edu.hw4.tasks.RandomAnimalGenerator.randomAnimal;
import static edu.hw4.tasks.RandomAnimalGenerator.randomType;
import static edu.hw4.tasks.RandomAnimalGenerator.randomWeight;
import static org.assertj.core.api.Assertions.assertThat;
import static edu.hw4.Animal.Sex.*;
import static edu.hw4.Animal.Type.*;

@DisplayName("Task 15")
class SummaryWeightOfTypesFromKtoLAgeTest {
    private static Animal animalByTypeAgeWeight(final Animal.Type type, final int age, final int weight) {
        return randomAnimal(null, type, null, age, null, weight, null);
    }

    private static Animal randomAnimalByAge(final int age) {
        return animalByTypeAgeWeight(randomType(), age, randomWeight());
    }

    public static Stream<Arguments> testSummaryWeightOfTypesFromKtoLAge() {
        return Stream.of(
            Arguments.of(
                List.of(),
                0,
                100,
                Map.of()
            ),
            Arguments.of(
                List.of(
                    randomAnimalByAge(5),
                    randomAnimalByAge(1),
                    randomAnimalByAge(20),
                    randomAnimalByAge(100),
                    randomAnimalByAge(10),
                    randomAnimalByAge(25),
                    randomAnimalByAge(30)
                ),
                11, 19,
                Map.of()
            ),
            Arguments.of(
                List.of(
                    randomAnimalByAge(13),
                    animalByTypeAgeWeight(DOG, 20, 50),
                    animalByTypeAgeWeight(CAT, 25, 1),
                    randomAnimalByAge(103),
                    animalByTypeAgeWeight(DOG, 21, 40),
                    animalByTypeAgeWeight(FISH, 30, 23),
                    animalByTypeAgeWeight(BIRD, 40, 12),
                    randomAnimalByAge(77),
                    randomAnimalByAge(83),
                    animalByTypeAgeWeight(FISH, 29, 4)
                ),
                15, 60,
                Map.of(
                    DOG, 90,
                    CAT, 1,
                    FISH, 27,
                    BIRD, 12
                )
            ),
            Arguments.of(
                List.of(
                    animalByTypeAgeWeight(FISH, 6, 13),
                    randomAnimalByAge(1),
                    animalByTypeAgeWeight(DOG, 25, 8),
                    animalByTypeAgeWeight(CAT, 5, 5),
                    randomAnimalByAge(4),
                    randomAnimalByAge(3),
                    animalByTypeAgeWeight(CAT, 10, 9),
                    randomAnimalByAge(101),
                    animalByTypeAgeWeight(DOG, 100, 3),
                    randomAnimalByAge(200),
                    animalByTypeAgeWeight(BIRD, 50, 500),
                    animalByTypeAgeWeight(CAT, 6, 36),
                    randomAnimalByAge(0),
                    randomAnimalByAge(199),
                    randomAnimalByAge(102),
                    animalByTypeAgeWeight(SPIDER, 7, 51),
                    animalByTypeAgeWeight(DOG, 23, 108),
                    randomAnimalByAge(4),
                    animalByTypeAgeWeight(FISH, 8, 1),
                    animalByTypeAgeWeight(SPIDER, 22, 33),
                    randomAnimalByAge(1),
                    animalByTypeAgeWeight(BIRD, 17, 9),
                    animalByTypeAgeWeight(SPIDER, 69, 420),
                    randomAnimalByAge(2),
                    randomAnimalByAge(200),
                    animalByTypeAgeWeight(CAT, 6, 66)
                ),
                5, 100,
                Map.of(
                    DOG, 119,
                    CAT, 116,
                    BIRD, 509,
                    FISH, 14,
                    SPIDER, 504
                )
            )
        );
    }

    @ParameterizedTest
    @MethodSource
    void testSummaryWeightOfTypesFromKtoLAge(
        final Collection<Animal> input, final int k, final int l, final Map<Animal.Type, Integer> output
    ) {
        assertThat(SummaryWeightOfTypesFromKtoLAge.count(input, k, l)).isEqualTo(output);
    }
}
