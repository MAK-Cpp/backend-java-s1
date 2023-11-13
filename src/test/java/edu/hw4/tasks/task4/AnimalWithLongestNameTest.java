package edu.hw4.tasks.task4;

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

@DisplayName("Task 4")
class AnimalWithLongestNameTest {
    public static Stream<Arguments> testAnimalWithLongestName() {
        return Stream.of(
            Arguments.of(
                List.of(),
                null
            ),
            Arguments.of(
                List.of(
                    new Animal("Crepidiastrixeris denticulatoplatyphylla", FISH, M, 2, 5, 4, true),
                    new Animal("Myxococcus llanfairpwllgwyngyllgogerychwyrndrobwllllantysiliogogogochensis", CAT, M, 1, 3, 2, true),
                    new Animal("Acidipropionibacterium acidipropionici", BIRD, F, 4, 2, 1, false),
                    new Animal("Gammaracanthuskytodermogammarus loricatobaicalensis Dybowski", DOG, F, 5, 1, 3, true),
                    new Animal("Sphaerechinorhynchus macropisthospinus", SPIDER, M, 3, 4, 5, true)
                ),
                new Animal("Myxococcus llanfairpwllgwyngyllgogerychwyrndrobwllllantysiliogogogochensis", CAT, M, 1, 3, 2, true)
            ),
            Arguments.of(
                List.of(
                    new Animal("LENTH = 9", FISH, M, 2, 5, 4, true),
                    new Animal("ABCDEFGHI", SPIDER, M, 3, 4, 5, true),
                    new Animal("hi barbie", DOG, F, 5, 1, 3, true)
                ),
                new Animal("LENTH = 9", FISH, M, 2, 5, 4, true)
            )
        );
    }

    @ParameterizedTest
    @MethodSource
    void testAnimalWithLongestName(final Collection<Animal> input, final Animal output) {
        assertThat(AnimalWithLongestName.find(input)).isEqualTo(output);
    }
}
