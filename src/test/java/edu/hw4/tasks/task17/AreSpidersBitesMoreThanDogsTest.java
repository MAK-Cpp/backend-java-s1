package edu.hw4.tasks.task17;

import edu.hw4.Animal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;
import static edu.hw4.Animal.Type.*;
import static edu.hw4.tasks.RandomAnimalGenerator.randomAnimal;
import static edu.hw4.tasks.RandomAnimalGenerator.randomBite;
import static edu.hw4.tasks.RandomAnimalGenerator.randomType;
import static edu.hw4.tasks.ShuffleList.shuffle;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Task 17")
class AreSpidersBitesMoreThanDogsTest {
    private static final Random RANDOM = new Random();

    private static Animal dogByBite(final boolean bite) {
        return randomAnimal(null, DOG, null, null, null, null, bite);
    }

    private static Animal spiderByBite(final boolean bite) {
        return randomAnimal(null, SPIDER, null, null, null, null, bite);
    }

    private static Animal otherAnimal() {
        Animal.Type type;
        do {
            type = randomType();
        } while (type == DOG || type == SPIDER);
        return randomAnimal(null, type, null, null, null, null, randomBite());
    }

    private static int randomAnimalCount() {
        return RANDOM.nextInt(0, 100);
    }

    private static Arguments createTest(
        final int countOtherAnimals,
        final int bitesDogs,
        final int notBitesDogs,
        final int bitesSpiders,
        final int notBitesSpiders,
        final Boolean answer
    ) {
        List<Animal> animals = new ArrayList<>();
        for (int i = 0; i < (countOtherAnimals == -1 ? randomAnimalCount() : countOtherAnimals); i++) {
            animals.add(otherAnimal());
        }
        for (int i = 0; i < (bitesDogs == -1 ? randomAnimalCount() : bitesDogs); i++) {
            animals.add(dogByBite(true));
        }
        for (int i = 0; i < (notBitesDogs == -1 ? randomAnimalCount() : notBitesDogs); i++) {
            animals.add(dogByBite(false));
        }
        for (int i = 0; i < (bitesSpiders == -1 ? randomAnimalCount() : bitesSpiders); i++) {
            animals.add(spiderByBite(true));
        }
        for (int i = 0; i < (notBitesSpiders == -1 ? randomAnimalCount() : notBitesSpiders); i++) {
            animals.add(spiderByBite(false));
        }
        return Arguments.of(shuffle(animals), answer);
    }

    public static Stream<Arguments> testAreSpidersBitesMoreThanDogs() {
        return Stream.of(
            createTest(0, 0, 0, 0, 0, false),
            createTest(-1, 0, 0, 0, 0, false),
            createTest(-1, -1, -1, 0, 0, false),
            createTest(-1, 0, 0, -1, -1, false),
            createTest(-1, 5, 0, 10, 0, false),
            createTest(-1, 5, 23, 10, 18, true)
        );
    }

    @ParameterizedTest
    @MethodSource
    void testAreSpidersBitesMoreThanDogs(final Collection<Animal> input, final Boolean output) {
        assertThat(AreSpidersBitesMoreThanDogs.check(input)).isEqualTo(output);
    }
}
