package edu.hw4.tasks.task10;

import edu.hw4.Animal;
import java.util.Collection;
import java.util.List;

public final class AgeDoesntMatchPawsAnimals {
    private AgeDoesntMatchPawsAnimals() {
    }

    public static List<Animal> find(final Collection<Animal> animals) {
        return animals.stream()
            .filter(x -> x.paws() != x.age())
            .toList();
    }
}
