package edu.hw4.tasks.task20;

import edu.hw4.Animal;
import edu.hw4.tasks.task19.FindErrorsInAnimals;
import edu.hw4.tasks.task19.ValidationError;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public final class FindErrorsInAnimalsVersion2 {
    private FindErrorsInAnimalsVersion2() {
    }

    public static Map<String, String> find(final Collection<Animal> animals) {
        return FindErrorsInAnimals.find(animals).entrySet().stream()
            .map(x -> Map.entry(
                x.getKey(),
                x.getValue().stream()
                    .sorted(ValidationError::compareTo)
                    .map(ValidationError::getErrorMessage)
                    .reduce("", (a, b) -> a + (a.isEmpty() ? "" : " ") + b)
            ))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
