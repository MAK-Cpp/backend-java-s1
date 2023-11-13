package edu.hw4.tasks.task13;

import edu.hw4.Animal;
import java.util.Collection;
import java.util.List;

public final class AnimalsWithLongName {
    private AnimalsWithLongName() {
    }

    public static List<Animal> find(final Collection<Animal> animals) {
        return animals.stream()
            .filter(x -> x.name().trim().split(" ").length > 2)
            .toList();
    }
}
