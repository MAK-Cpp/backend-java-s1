package edu.hw4.tasks.task2;

import edu.hw4.Animal;
import java.util.Collection;
import java.util.List;

public final class FirstKHeavySort {
    private FirstKHeavySort() {
    }

    public static List<Animal> sort(final Collection<Animal> animals, final int k) {
        return animals.stream()
            .sorted((a, b) -> Integer.compare(b.weight(), a.weight()))
            .limit(k)
            .toList();
    }
}
