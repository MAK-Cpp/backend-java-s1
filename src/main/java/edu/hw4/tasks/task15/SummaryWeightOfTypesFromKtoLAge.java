package edu.hw4.tasks.task15;

import edu.hw4.Animal;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public final class SummaryWeightOfTypesFromKtoLAge {
    private SummaryWeightOfTypesFromKtoLAge() {
    }

    public static Map<Animal.Type, Integer> count(final Collection<Animal> animals, int k, int l) {
        return animals.stream()
            .filter(x -> k <= x.age() && x.age() <= l)
            .collect(Collectors.toMap(Animal::type, Animal::weight, Integer::sum));
    }
}
