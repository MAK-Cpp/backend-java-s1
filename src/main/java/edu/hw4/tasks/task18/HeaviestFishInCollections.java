package edu.hw4.tasks.task18;

import edu.hw4.Animal;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class HeaviestFishInCollections {
    private HeaviestFishInCollections() {
    }

    public static Animal find(final Collection<Collection<Animal>> listsOfAnimals) {
        return listsOfAnimals.stream()
            .reduce(Stream.ofNullable((Animal) null),
                (accum, curr) -> Stream.concat(accum, curr.stream().filter(x -> x.type() == Animal.Type.FISH)),
                (a, b) -> b
            )
            .collect(Collectors.toMap(x -> x, s -> 1, Integer::sum))
            .entrySet().stream()
            .filter(x -> x.getValue() > 1)
            .map(Map.Entry::getKey)
            .max(Comparator.comparingInt(Animal::weight))
            .orElse(null);
    }
}
