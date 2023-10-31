package edu.hw4.tasks.task7;

import edu.hw4.Animal;
import java.util.Collection;

public final class KOldestAnimal {
    private KOldestAnimal() {
    }

    public static Animal find(final Collection<Animal> animals, final int k) {
        return animals.stream()
            .sorted((x, y) -> Integer.compare(y.age(), x.age()))
            .skip(k - 1)
            .findFirst()
            .orElse(null);
    }
}
