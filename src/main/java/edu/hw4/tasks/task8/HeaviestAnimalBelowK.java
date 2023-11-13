package edu.hw4.tasks.task8;

import edu.hw4.Animal;
import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;

public final class HeaviestAnimalBelowK {
    private HeaviestAnimalBelowK() {
    }

    public static Optional<Animal> find(final Collection<Animal> animals, final int k) {
        return animals.stream()
            .filter(x -> x.height() < k)
            .max(Comparator.comparingInt(Animal::weight));
    }
}
