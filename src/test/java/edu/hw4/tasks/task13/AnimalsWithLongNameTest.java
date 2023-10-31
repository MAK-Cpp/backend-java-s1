package edu.hw4.tasks.task13;

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
import static edu.hw4.Animal.Sex.*;

@DisplayName("Task 13")
class AnimalsWithLongNameTest {
    private static Animal animalByName(final String name) {
        return randomAnimal(name, null, null, null, null, null, null);
    }

    public static Stream<Arguments> testAnimalsWithLongName() {
        return Stream.of(
            Arguments.of(
                List.of(),
                List.of()
            ),
            Arguments.of(
                List.of(
                    animalByName(null),
                    animalByName("first second"),
                    animalByName(null),
                    animalByName(null),
                    animalByName(null),
                    animalByName("1 2"),
                    animalByName("01 10")
                ),
                List.of()
            ),
            Arguments.of(
                List.of(
                    animalByName("a b"),
                    animalByName(null),
                    animalByName("c d e"),
                    animalByName("f g"),
                    animalByName("hga i g+1 k 3o 12g=p q asdr"),
                    animalByName("s t"),
                    animalByName(null)
                ),
                List.of(2, 4)
            )
        );
    }

    @ParameterizedTest
    @MethodSource
    void testAnimalsWithLongName(final Collection<Animal> input, final List<Integer> answers) {
        final List<Animal> inputList = input.stream().toList();
        final List<Animal> output = AnimalsWithLongName.find(input);

        assertThat(output.size()).isEqualTo(answers.size());
        for (int i = 0; i < answers.size(); i++) {
            assertThat(output.get(i)).isEqualTo(inputList.get(answers.get(i)));
        }
    }
}
