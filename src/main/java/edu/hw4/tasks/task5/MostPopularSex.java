package edu.hw4.tasks.task5;

import edu.hw4.Animal;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public final class MostPopularSex {
    private MostPopularSex() {
    }

    public static Animal.Sex find(final Collection<Animal> animals) {
        final Map.Entry<Animal.Sex, Integer> ans = animals
            .stream()
            .collect(Collectors.toMap(Animal::sex, e -> 1, Integer::sum))
            .entrySet()
            .stream()
            .max((a, b) -> a.getValue().equals(b.getValue())
                ? a.getKey().compareTo(b.getKey())
                : Integer.compare(a.getValue(), b.getValue()))
            .orElse(null);
        return ans == null ? null : ans.getKey();
    }
}
