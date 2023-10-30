package edu.hw4.tasks.task5;

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

class MostPopularSexTest {
    private static final Animal MALE = new Animal("", DOG, M, 0, 0, 0, false);
    private static final Animal FEMALE = new Animal("", DOG, F, 0, 0, 0, false);

    private static Stream<Arguments> testMostPopularSex() {
        return Stream.of(
            Arguments.of(
                List.of(),
                null
            ),
            Arguments.of(
                List.of(MALE, MALE, MALE),
                M
            ),
            Arguments.of(
                List.of(FEMALE, FEMALE, FEMALE, FEMALE, FEMALE),
                F
            ),
            Arguments.of(
                List.of(MALE, FEMALE, MALE, MALE, FEMALE, FEMALE, MALE, FEMALE, FEMALE, FEMALE, MALE),
                F
            ),
            Arguments.of(
                List.of(MALE, FEMALE, FEMALE, FEMALE, FEMALE, MALE, MALE, MALE, MALE),
                M
            ),
            Arguments.of(
                List.of(MALE, MALE, FEMALE, FEMALE),
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
