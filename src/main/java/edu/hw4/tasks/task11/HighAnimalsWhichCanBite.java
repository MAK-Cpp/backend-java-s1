package edu.hw4.tasks.task11;

import edu.hw4.Animal;
import java.util.Collection;
import java.util.List;

public final class HighAnimalsWhichCanBite {
    private static final int HEIGHT_OF_A_TALL_ANIMAL = 100;

    private HighAnimalsWhichCanBite() {
    }

    public static List<Animal> find(final Collection<Animal> animals) {
        return animals.stream()
            .filter(x -> x.bites() && (x.height() > HEIGHT_OF_A_TALL_ANIMAL))
            .toList();
    }
}
