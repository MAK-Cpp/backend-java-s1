package edu.hw4.tasks.task6;

import edu.hw4.Animal;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class HeaviestAnimalOfEachType {
    private HeaviestAnimalOfEachType() {
    }

    public static Map<Animal.Type, Animal> find(final Collection<Animal> animals) {
        return animals.stream()
            .collect(Collectors.toMap(
                Animal::type,
                Function.identity(),
                BinaryOperator.maxBy(Comparator.comparing(Animal::weight))
            ));
    }
}
