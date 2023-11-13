package edu.hw4.tasks.task12;

import edu.hw4.Animal;
import java.util.Collection;

public final class AnimalsWhoseWeightExceedsTheirHeight {
    private AnimalsWhoseWeightExceedsTheirHeight() {
    }

    public static Integer count(final Collection<Animal> animals) {
        return (int) animals.stream()
            .filter(x -> x.weight() > x.height())
            .count();
    }
}
