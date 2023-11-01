package edu.hw4.tasks.task17;

import edu.hw4.Animal;
import java.util.Collection;

public final class AreSpidersBitesMoreThanDogs {
    private AreSpidersBitesMoreThanDogs() {
    }

    public static Boolean check(final Collection<Animal> animals) {
        return animals.stream()
            .reduce(
                Counter.of(0, 0, 0),
                (accum, animal) -> switch (animal.type()) {
                    case Animal.Type.SPIDER -> (animal.bites() ? accum.addSpider() : accum);
                    case Animal.Type.DOG -> (animal.bites() ? accum.addDog() : accum);
                    default -> accum;
                },
                Counter::add
            ).isCorrect();
    }
}
