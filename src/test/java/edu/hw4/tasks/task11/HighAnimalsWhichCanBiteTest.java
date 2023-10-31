package edu.hw4.tasks.task11;

import edu.hw4.Animal;
import edu.hw4.tasks.RandomAnimalGenerator;
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

@DisplayName("Task 11")
class HighAnimalsWhichCanBiteTest {
    private static Animal animalByHeightAndBite(int height, Boolean bite) {
        return new Animal(
            RandomAnimalGenerator.randomName(),
            RandomAnimalGenerator.randomType(),
            RandomAnimalGenerator.randomSex(),
            RandomAnimalGenerator.randomAge(),
            height,
            RandomAnimalGenerator.randomWeight(),
            bite
        );
    }

    public static Stream<Arguments> testHighAnimalsWhichCanBite() {
        return Stream.of(
            Arguments.of(List.of(), List.of()),
            Arguments.of(
                List.of(
                    animalByHeightAndBite(30, true),
                    animalByHeightAndBite(60, null),
                    animalByHeightAndBite(101, false)
                ),
                List.of()
            ),
            Arguments.of(
                List.of(
                    animalByHeightAndBite(100, null),
                    animalByHeightAndBite(99, false),
                    animalByHeightAndBite(101, true),
                    animalByHeightAndBite(173, null),
                    animalByHeightAndBite(109, false),
                    animalByHeightAndBite(110, true)
                ),
                List.of(2, 3, 5)
            )
        );
    }

    @ParameterizedTest
    @MethodSource
    void testHighAnimalsWhichCanBite(final Collection<Animal> input, final List<Integer> answers) {
        final List<Animal> inputList = input.stream().toList();
        final List<Animal> output = HighAnimalsWhichCanBite.find(input);

        assertThat(output.size()).isEqualTo(answers.size());
        for (int i = 0; i < answers.size(); i++) {
            assertThat(output.get(i)).isEqualTo(inputList.get(answers.get(i)));
        }
    }
}
