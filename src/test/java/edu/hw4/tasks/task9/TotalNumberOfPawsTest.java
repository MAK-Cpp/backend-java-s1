package edu.hw4.tasks.task9;

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

@DisplayName("Task 9")
class TotalNumberOfPawsTest {
    private static Animal dog() {
        return randomAnimal(null, DOG, null, null, null, null, null);
    }

    private static Animal cat() {
        return randomAnimal(null, CAT, null, null, null, null, null);
    }

    private static Animal bird() {
        return randomAnimal(null, BIRD, null, null, null, null, null);
    }

    private static Animal fish() {
        return randomAnimal(null, FISH, null, null, null, null, null);
    }

    private static Animal spider() {
        return randomAnimal(null, SPIDER, null, null, null, null, null);
    }

    public static Stream<Arguments> testTotalNumberOfPaws() {
        return Stream.of(
            Arguments.of(List.of(), 0),
            Arguments.of(List.of(dog(), dog(), dog(), cat(), cat(), fish(), fish(), fish(), fish()), 20),
            Arguments.of(List.of(
                bird(),
                spider(),
                dog(),
                cat(),
                cat(),
                dog(),
                bird(),
                bird(),
                spider(),
                fish(),
                fish()
            ), 38)
        );
    }

    @ParameterizedTest
    @MethodSource
    void testTotalNumberOfPaws(final Collection<Animal> input, final Integer output) {
        assertThat(TotalNumberOfPaws.count(input)).isEqualTo(output);
    }
}
