package edu.hw4.tasks.task19;

import edu.hw4.Animal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Task 19")
class FindErrorsInAnimalsTest {
    public static Stream<Arguments> testFindErrorsInAnimals() {
        return Stream.of(
            Arguments.of(
                List.of(),
                Map.of()
            ),
            Arguments.of(
                List.of(new Animal("", Animal.Type.DOG, Animal.Sex.M, 3, 30, 40, false)),
                Map.of("", Set.of(ValidationError.WRONG_NAME))
            ),
            Arguments.of(
                List.of(
                    new Animal("name", Animal.Type.CAT, Animal.Sex.F, -1, 1, 1, true),
                    new Animal("surname", Animal.Type.CAT, Animal.Sex.M, Animal.Type.CAT.getMaximumAge() + 1, 1, 1, false)
                ),
                Map.of("name", Set.of(ValidationError.WRONG_AGE), "surname", Set.of(ValidationError.WRONG_AGE))
            ),
            Arguments.of(
                List.of(
                    new Animal("Bear", Animal.Type.BIRD, Animal.Sex.M, 1, -1, 1, false),
                    new Animal("Beer", Animal.Type.BIRD, Animal.Sex.F, 1, Animal.Type.BIRD.getMaximumHeight() + 1, 1, true)
                ),
                Map.of("Bear", Set.of(ValidationError.WRONG_HEIGHT), "Beer", Set.of(ValidationError.WRONG_HEIGHT))
            ),
            Arguments.of(
                List.of(
                    new Animal("Minecraft", Animal.Type.FISH, Animal.Sex.F, 1, 1, -1, false),
                    new Animal("Terraria", Animal.Type.SPIDER, Animal.Sex.M, 1, 1, Animal.Type.SPIDER.getMaximumWeight() + 1, true)
                ),
                Map.of("Minecraft", Set.of(ValidationError.WRONG_WEIGHT), "Terraria", Set.of(ValidationError.WRONG_WEIGHT))
            ),
            Arguments.of(
                List.of(
                    new Animal("", Animal.Type.DOG, Animal.Sex.M, Animal.Type.DOG.getMaximumAge() + 1, 30, 40, false),
                    new Animal("Beer", Animal.Type.BIRD, Animal.Sex.F, 1, 1 + 1, 1, true),
                    new Animal("surname", Animal.Type.CAT, Animal.Sex.F, Animal.Type.CAT.getMaximumAge() + 1, 1, -1, false),
                    new Animal("Bear", Animal.Type.BIRD, Animal.Sex.F, -1, -1, Animal.Type.BIRD.getMaximumWeight() + 1, false),
                    new Animal("Minecraft", Animal.Type.FISH, Animal.Sex.F, 1, 1, 1, false),
                    new Animal("Terraria", Animal.Type.SPIDER, Animal.Sex.M, 1, 1, Animal.Type.SPIDER.getMaximumWeight() + 1, true)
                ),
                Map.of(
                    "", Set.of(ValidationError.WRONG_NAME, ValidationError.WRONG_AGE),
                    "surname", Set.of(ValidationError.WRONG_AGE, ValidationError.WRONG_WEIGHT),
                    "Bear", Set.of(ValidationError.WRONG_AGE, ValidationError.WRONG_HEIGHT, ValidationError.WRONG_WEIGHT),
                    "Terraria", Set.of(ValidationError.WRONG_WEIGHT)
                )
            )
        );
    }

    @ParameterizedTest
    @MethodSource
    void testFindErrorsInAnimals(final Collection<Animal> input, final Map<String, Set<ValidationError>> output) {
        assertThat(FindErrorsInAnimals.find(input)).isEqualTo(output);
    }
}
