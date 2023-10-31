package edu.hw4.tasks.task9;

import edu.hw4.Animal;
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

@DisplayName("Task 9")
class TotalNumberOfPawsTest {
    private static final Animal dog = new Animal("", DOG, M, 0, 0, 0, false);
    private static final Animal cat = new Animal("", CAT, M, 0, 0, 0, false);
    private static final Animal bird = new Animal("", BIRD, M, 0, 0, 0, false);
    private static final Animal fish = new Animal("", FISH, M, 0, 0, 0, false);
    private static final Animal spider = new Animal("", SPIDER, M, 0, 0, 0, false);

    public static Stream<Arguments> testTotalNumberOfPaws() {
        return Stream.of(
            Arguments.of(List.of(), 0),
            Arguments.of(List.of(dog, dog, dog, cat, cat, fish, fish, fish, fish), 20),
            Arguments.of(List.of(bird, spider, dog, cat, cat, dog, bird, bird, spider, fish, fish), 38)
        );
    }

    @ParameterizedTest
    @MethodSource
    void testTotalNumberOfPaws(final Collection<Animal> input, final Integer output) {
        assertThat(TotalNumberOfPaws.count(input)).isEqualTo(output);
    }
}
