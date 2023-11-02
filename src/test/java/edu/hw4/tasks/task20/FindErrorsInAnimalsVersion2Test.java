package edu.hw4.tasks.task20;

import edu.hw4.Animal;
import edu.hw4.tasks.task19.ValidationError;
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

@DisplayName("Task 20")
class FindErrorsInAnimalsVersion2Test {
    public static Stream<Arguments> testFindErrorsInAnimalsVersion2() {
        return Stream.of(
            Arguments.of(
                List.of(),
                Map.of()
            ),
            Arguments.of(
                List.of(new Animal("", Animal.Type.DOG, Animal.Sex.M, 3, 30, 40, false)),
                Map.of("", ValidationError.WRONG_NAME.getErrorMessage())
            ),
            Arguments.of(
                List.of(
                    new Animal("name", Animal.Type.CAT, Animal.Sex.F, -1, 1, 1, true),
                    new Animal("surname", Animal.Type.CAT, Animal.Sex.M, Animal.Type.CAT.getMaximumAge() + 1, 1, 1, false)
                ),
                Map.of("name", ValidationError.WRONG_AGE.getErrorMessage(), "surname", ValidationError.WRONG_AGE.getErrorMessage())
            ),
            Arguments.of(
                List.of(
                    new Animal("Bear", Animal.Type.BIRD, Animal.Sex.M, 1, -1, 1, false),
                    new Animal("Beer", Animal.Type.BIRD, Animal.Sex.F, 1, Animal.Type.BIRD.getMaximumHeight() + 1, 1, true)
                ),
                Map.of("Bear", ValidationError.WRONG_HEIGHT.getErrorMessage(), "Beer", ValidationError.WRONG_HEIGHT.getErrorMessage())
            ),
            Arguments.of(
                List.of(
                    new Animal("Minecraft", Animal.Type.FISH, Animal.Sex.F, 1, 1, -1, false),
                    new Animal("Terraria", Animal.Type.SPIDER, Animal.Sex.M, 1, 1, Animal.Type.SPIDER.getMaximumWeight() + 1, true)
                ),
                Map.of("Minecraft", ValidationError.WRONG_WEIGHT.getErrorMessage(), "Terraria", ValidationError.WRONG_WEIGHT.getErrorMessage())
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
                    "", ValidationError.WRONG_NAME.getErrorMessage() + " " + ValidationError.WRONG_AGE.getErrorMessage(),
                    "surname", ValidationError.WRONG_AGE.getErrorMessage() + " " + ValidationError.WRONG_WEIGHT.getErrorMessage(),
                    "Bear", ValidationError.WRONG_AGE.getErrorMessage() + " " + ValidationError.WRONG_HEIGHT.getErrorMessage() + " " + ValidationError.WRONG_WEIGHT.getErrorMessage(),
                    "Terraria", ValidationError.WRONG_WEIGHT.getErrorMessage()
                )
            )
        );
    }

    @ParameterizedTest
    @MethodSource
    void testFindErrorsInAnimalsVersion2(final Collection<Animal> input, final Map<String, String> output) {
        assertThat(FindErrorsInAnimalsVersion2.find(input)).isEqualTo(output);
    }
}
