package edu.hw4.tasks.task4;

import edu.hw4.Animal;
import java.util.Collection;
import java.util.Comparator;

public final class AnimalWithLongestName {
    private AnimalWithLongestName() {
    }

    public static Animal find(final Collection<Animal> animals) {
        return animals.stream()
            .max(Comparator.comparingInt(x -> x.name().length()))
            .orElse(null);
    }
}
