package edu.hw4.tasks.task3;

import edu.hw4.Animal;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public final class AnimalTypeCount {
    private AnimalTypeCount() {}

    public static Map<Animal.Type, Integer> count(final Collection<Animal> animals) {
        return animals.stream()
            .collect(Collectors.toMap(Animal::type, s -> 1, Integer::sum));
    }
}
