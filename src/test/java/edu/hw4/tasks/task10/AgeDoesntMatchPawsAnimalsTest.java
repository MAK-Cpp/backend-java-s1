package edu.hw4.tasks.task10;

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
import static edu.hw4.Animal.Type.*;

@DisplayName("Task 10")
class AgeDoesntMatchPawsAnimalsTest {
    private static Animal animalByTypeAndAge(final Animal.Type type, final int age) {
        return randomAnimal(null, type, null, age, null, null, null);
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
                List.of(0, 1, 3, 4, 5, 7)
            )
        );
    }

    @ParameterizedTest
    @MethodSource
    void testAgeDoesntMatchPawsAnimals(final Collection<Animal> input, final List<Integer> answers) {
        List<Animal> inputList = input.stream().toList();
        List<Animal> output = AgeDoesntMatchPawsAnimals.find(input);

        assertThat(output.size()).isEqualTo(answers.size());
        for (int i = 0; i < answers.size(); i++) {
            assertThat(inputList.get(answers.get(i))).isEqualTo(output.get(i));
        }
    }
}
