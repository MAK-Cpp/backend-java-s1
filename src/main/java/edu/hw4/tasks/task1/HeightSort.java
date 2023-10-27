package edu.hw4.tasks.task1;

import edu.hw4.Animal;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public final class HeightSort {
    private HeightSort() {
    }

    public static List<Animal> sort(Collection<Animal> animals) {
        return animals.stream()
            .sorted(Comparator.comparingInt(Animal::height))
            .toList();
    }
}
