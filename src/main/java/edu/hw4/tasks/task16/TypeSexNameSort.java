package edu.hw4.tasks.task16;

import edu.hw4.Animal;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public final class TypeSexNameSort {
    private TypeSexNameSort() {
    }

    public static List<Animal> sort(final Collection<Animal> animals) {
        return animals.stream()
            .sorted(Comparator.comparing(Animal::type).thenComparing(Animal::sex).thenComparing(Animal::name))
            .toList();
    }
}
