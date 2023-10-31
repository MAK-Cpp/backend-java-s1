package edu.hw4.tasks.task5;

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
import static edu.hw4.Animal.Sex.*;

@DisplayName("Task 5")
class MostPopularSexTest {
    private static Animal male() {
        return randomAnimal(null, null, M, null, null, null, null);
    }

    private static Animal female() {
        return randomAnimal(null, null, F, null, null, null, null);
    }

    private static Stream<Arguments> testMostPopularSex() {
        return Stream.of(
            Arguments.of(
                List.of(),
                null
            ),
            Arguments.of(
                List.of(male(), male(), male()),
                M
            ),
            Arguments.of(
                List.of(female(), female(), female(), female(), female()),
                F
            ),
            Arguments.of(
                List.of(male(), female(), male(), male(), female(), female(), male(), female(), female(), female(), male()),
                F
            ),
            Arguments.of(
                List.of(male(), female(), female(), female(), female(), male(), male(), male(), male()),
                M
            ),
            Arguments.of(
                List.of(male(), male(), female(), female()),
                F
            )
        );
    }

    @ParameterizedTest
    @MethodSource
    void testMostPopularSex(final Collection<Animal> input, final Animal.Sex output) {
        assertThat(MostPopularSex.find(input)).isEqualTo(output);
    }
}
