package edu.hw4.tasks.task18;

import edu.hw4.Animal;
import java.util.Collection;
import java.util.Comparator;

public final class HeaviestFishInCollections {
    private HeaviestFishInCollections() {
    }

    public static Animal find(final Collection<Collection<Animal>> listsOfAnimals) {
        return listsOfAnimals.stream()
            .flatMap(x -> x.stream().filter(y -> y.type() == Animal.Type.FISH))
            .max(Comparator.comparingInt(Animal::weight))
            .orElse(null);
    }
}
