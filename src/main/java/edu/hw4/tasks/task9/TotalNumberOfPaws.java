package edu.hw4.tasks.task9;

import edu.hw4.Animal;
import java.util.Collection;

public final class TotalNumberOfPaws {
    private TotalNumberOfPaws() {
    }

    public static Integer count(final Collection<Animal> animals) {
        return animals.stream()
            .reduce(0, (x, y) -> x + y.paws(), Integer::sum);
    }
}
