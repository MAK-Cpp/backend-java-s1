package edu.hw4.tasks.task11;

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

class HighAnimalsWhichCanBiteTest {
    private static Animal animalByHeightAndBite(int height, Boolean bite) {
        return new Animal("", DOG, M, 0, height, 0, bite);
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
                List.of(
                    animalByHeightAndBite(101, true),
                    animalByHeightAndBite(173, null),
                    animalByHeightAndBite(110, true)
                )
            )
        );
    }

    @ParameterizedTest
    @MethodSource
    void testHighAnimalsWhichCanBite(final Collection<Animal> input, final List<Animal> output) {
        assertThat(HighAnimalsWhichCanBite.find(input)).isEqualTo(output);
    }
}
