package edu.hw4.tasks.task17;

import edu.hw4.Animal;
import java.util.Collection;

public final class AreSpidersBitesMoreThanDogs {
    private AreSpidersBitesMoreThanDogs() {
    }

    public static Boolean check(final Collection<Animal> animals) {
        return animals.stream()
            .reduce(
                Counter.of(),
                (accum, animal) -> switch (animal.type()) {
                    case Animal.Type.SPIDER -> accum.addSpider(animal.bites());
                    case Animal.Type.DOG -> accum.addDog(animal.bites());
                    default -> accum;
                },
                Counter::add
            ).isCorrect();
    }
}
