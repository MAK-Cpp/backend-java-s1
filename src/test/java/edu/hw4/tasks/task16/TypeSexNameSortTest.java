package edu.hw4.tasks.task16;

import edu.hw4.Animal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import static edu.hw4.Animal.Sex.*;
import static edu.hw4.Animal.Type.*;
import static edu.hw4.tasks.RandomAnimalGenerator.randomAnimal;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Task 16")
class TypeSexNameSortTest {
    private static Animal animalByNameTypeSex(final String name, final Animal.Type type, final Animal.Sex sex) {
        return randomAnimal(name, type, sex, null, null, null, null);
    }
    public static Stream<List<Animal>> testTypeSexNameSort() {
        return Stream.of(
            List.of(),
            List.of(
                animalByNameTypeSex("z", DOG, M),
                animalByNameTypeSex("a", DOG, F),
                animalByNameTypeSex("b", DOG, F),
                animalByNameTypeSex("v", FISH, F),
                animalByNameTypeSex("a", SPIDER, M),
                animalByNameTypeSex("z", SPIDER, M),
                animalByNameTypeSex("b", SPIDER, F),
                animalByNameTypeSex("y", SPIDER, F)
            ),
            List.of(
                animalByNameTypeSex("abcd", CAT, M),
                animalByNameTypeSex("abce", CAT, M),
                animalByNameTypeSex("xyz", CAT, M),
                animalByNameTypeSex("wzz", CAT, F),
                animalByNameTypeSex("xzz", CAT, F),
                animalByNameTypeSex("yzz", CAT, F),
                animalByNameTypeSex("zzz", CAT, F),
                animalByNameTypeSex("bobik", DOG, M),
                animalByNameTypeSex("bublik", DOG, M),
                animalByNameTypeSex("dolik", DOG, M),
                animalByNameTypeSex("peach", DOG, F),
                animalByNameTypeSex("princess", DOG, F),
                animalByNameTypeSex("fly", BIRD, M),
                animalByNameTypeSex("or die", BIRD, M),
                animalByNameTypeSex("EEEEGGGGGSSSSS", BIRD, F),
                animalByNameTypeSex("catfish", FISH, M),
                animalByNameTypeSex("dogfish", FISH, M),
                animalByNameTypeSex("biba", FISH, F),
                animalByNameTypeSex("yasos", FISH, F),
                animalByNameTypeSex("man", SPIDER, M),
                animalByNameTypeSex("miles morales", SPIDER, M),
                animalByNameTypeSex("peter parker", SPIDER, M),
                animalByNameTypeSex("svin", SPIDER, M),
                animalByNameTypeSex("gwen stacy", SPIDER, F),
                animalByNameTypeSex("women", SPIDER, F)
            )
        );
    }

    @ParameterizedTest
    @MethodSource
    void testTypeSexNameSort(final List<Animal> output) {
        List<Animal> input = new java.util.ArrayList<>(output);
        for (int i = 0; i < 100 && input.equals(output); i++) {
            Collections.shuffle(input);
        }
        assertThat(TypeSexNameSort.sort(input)).isEqualTo(output);
    }
}
