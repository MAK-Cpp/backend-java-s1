package edu.hw4.tasks.task6;

import edu.hw4.Animal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import static edu.hw4.tasks.RandomAnimalGenerator.randomAnimal;
import static org.assertj.core.api.Assertions.assertThat;
import static edu.hw4.Animal.Type.*;

@DisplayName("Task 6")
class HeaviestAnimalOfEachTypeTest {
    private static Animal createAnimal(Animal.Type type, int weight) {
        return randomAnimal(null, type, null, null, null, weight, null);
    }

    public static Stream<Arguments> testHeaviestAnimalOfEachType() {
        return Stream.of(
            Arguments.of(
                List.of(),
                Map.of()
            ),
            Arguments.of(
                List.of(
                    createAnimal(DOG, 10),
                    createAnimal(CAT, 2),
                    createAnimal(BIRD, 8),
                    createAnimal(BIRD, 13),
                    createAnimal(CAT, 44),
                    createAnimal(DOG, 1),
                    createAnimal(BIRD, 1000),
                    createAnimal(SPIDER, 99),
                    createAnimal(FISH, 123),
                    createAnimal(FISH, 5),
                    createAnimal(CAT, 666),
                    createAnimal(DOG, 19),
                    createAnimal(BIRD, 52)
                ),
                Map.of(
                    DOG, 11,
                    CAT, 10,
                    BIRD, 6,
                    SPIDER, 7,
                    FISH, 8
                )
            ),
            Arguments.of(
                List.of(
                    createAnimal(DOG, 15),
                    createAnimal(SPIDER, 9),
                    createAnimal(CAT, 14),
                    createAnimal(SPIDER, 10),
                    createAnimal(CAT, 11),
                    createAnimal(DOG, 13),
                    createAnimal(FISH, 12)
                ),
                Map.of(
                    DOG, 0,
                    SPIDER, 3,
                    CAT, 2,
                    FISH, 6
                )
            )
        );
    }

    @ParameterizedTest
    @MethodSource
    void testHeaviestAnimalOfEachType(final Collection<Animal> input, final Map<Animal.Type, Integer> answers) {
        final List<Animal> inputList = input.stream().toList();

        final Map<Animal.Type, Animal> output = new HashMap<>();
        for (Animal.Type type : answers.keySet()) {
            output.put(type, inputList.get(answers.get(type)));
        }
        assertThat(HeaviestAnimalOfEachType.find(input)).isEqualTo(output);
    }
}
