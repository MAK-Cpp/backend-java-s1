package edu.hw4.tasks.task14;

import edu.hw4.Animal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;
import static edu.hw4.tasks.RandomAnimalGenerator.randomAnimal;
import static edu.hw4.tasks.RandomAnimalGenerator.randomType;
import static org.assertj.core.api.Assertions.assertThat;
import static edu.hw4.Animal.Type.*;

@DisplayName("Task 14")
class IsThereDogsTallerThanKTest {
    private static Animal notDogByHeight(final int height) {
        Animal.Type type;
        do {
            type = randomType();
        } while (type == DOG);
        return randomAnimal(null, type, null, null, height, null, null);
    }

    private static Animal dogByHeight(final int height) {
        return randomAnimal(null, DOG, null, null, height, null, null);
    }

    public static Stream<Arguments> testIsThereDogsTallerThanK() {
        return Stream.of(
            Arguments.of(
                List.of(),
                10,
                false
            ),
            Arguments.of(
                List.of(
                    notDogByHeight(15),
                    notDogByHeight(20),
                    notDogByHeight(18),
                    notDogByHeight(13),
                    dogByHeight(3)
                ),
                5,
                false
            ),
            Arguments.of(
                List.of(
                    notDogByHeight(123),
                    dogByHeight(40),
                    dogByHeight(30),
                    notDogByHeight(34),
                    dogByHeight(35)
                ),
                35,
                true
            )
        );
    }

    @ParameterizedTest
    @MethodSource
    void testIsThereDogsTallerThanK(final Collection<Animal> input, final int k, final boolean output) {
        assertThat(IsThereDogsTallerThanK.check(input, k)).isEqualTo(output);
    }
}
