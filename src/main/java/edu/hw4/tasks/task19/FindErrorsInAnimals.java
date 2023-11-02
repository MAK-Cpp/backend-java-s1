package edu.hw4.tasks.task19;

import edu.hw4.Animal;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public final class FindErrorsInAnimals {
    private FindErrorsInAnimals() {

    }

    public static Set<ValidationError> getErrors(final Animal animal) {
        Set<ValidationError> answer = new java.util.HashSet<>(Set.of());
        if (animal.name().isEmpty()) {
            answer.add(ValidationError.WRONG_NAME);
        }
        if (animal.age() < 0 || animal.age() > animal.type().getMaximumAge()) {
            answer.add(ValidationError.WRONG_AGE);
        }
        if (animal.height() <= 0 || animal.height() > animal.type().getMaximumHeight()) {
            answer.add(ValidationError.WRONG_HEIGHT);
        }
        if (animal.weight() <= 0 || animal.weight() > animal.type().getMaximumWeight()) {
            answer.add(ValidationError.WRONG_WEIGHT);
        }
        return answer;
    }

    public static Map<String, Set<ValidationError>> find(final Collection<Animal> animals) {
        return animals.stream()
            .map(x -> Map.entry(x, getErrors(x)))
            .filter(x -> !x.getValue().isEmpty())
            .collect(Collectors.toMap(x -> x.getKey().name(), Map.Entry::getValue));
    }
}
