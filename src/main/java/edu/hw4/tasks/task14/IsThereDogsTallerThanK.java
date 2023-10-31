package edu.hw4.tasks.task14;

import edu.hw4.Animal;
import java.util.Collection;

public final class IsThereDogsTallerThanK {
    private IsThereDogsTallerThanK() {
    }

    public static boolean check(final Collection<Animal> animals, final int k) {
        return !animals.stream()
            .filter(x -> x.type() == Animal.Type.DOG && x.height() > k)
            .toList().isEmpty();
    }
}
