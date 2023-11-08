package edu.hw4.tasks.task18;

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

@DisplayName("Task 18")
class HeaviestFishInCollectionsTest {
    private static Animal notFish() {
        Animal.Type type;
        do {
            type = randomType();
        } while (type == Animal.Type.FISH);
        return randomAnimal(null, type, null, null, null, null, null);
    }
    public static Stream<Arguments> testHeaviestFishInCollections() {
        Animal[] inputFish = new Animal[20];
        for (int i = 0; i < 20; i++) {
            inputFish[i] = randomAnimal(null, Animal.Type.FISH, null, null, null, (i + 1) * 5, null);
        }
        return Stream.of(
            Arguments.of(
                List.of(
                    List.of(notFish(), inputFish[1], inputFish[2], notFish(), inputFish[1])
                ), 0, 2
            ),
            Arguments.of(
                List.of(), -1, -1
            ),
            Arguments.of(
                List.of(
                    List.of(inputFish[2], inputFish[15], notFish()),
                    List.of(notFish(), notFish(), notFish()),
                    List.of(inputFish[1], notFish(), notFish(), inputFish[1], notFish(), inputFish[1]),
                    List.of(inputFish[19]),
                    List.of(notFish(), notFish(), notFish(), notFish(), notFish(), inputFish[2])
                ), 3, 0
            ),
            Arguments.of(
                List.of(
                    List.of(inputFish[2], inputFish[0], notFish()),
                    List.of(notFish(), notFish(), notFish()),
                    List.of(inputFish[1], notFish(), notFish(), inputFish[3], notFish(), inputFish[4]),
                    List.of(notFish()),
                    List.of(notFish(), notFish(), notFish(), notFish(), notFish(), inputFish[5])
                ), 4, 5
            )
        );
    }

    @ParameterizedTest
    @MethodSource
    void testHeaviestFishInCollections(final Collection<Collection<Animal>> input, final int answerCollection, final int answerIndex) {
        if (answerCollection == -1 && answerIndex == -1) {
            assertThat(HeaviestFishInCollections.find(input)).isNull();
        } else {
            final Animal output = input.stream().toList().get(answerCollection).stream().toList().get(answerIndex);
            assertThat(HeaviestFishInCollections.find(input)).isEqualTo(output);
        }

    }
}
