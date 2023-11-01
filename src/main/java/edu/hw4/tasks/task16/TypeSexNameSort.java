package edu.hw4.tasks.task16;

import edu.hw4.Animal;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public final class TypeSexNameSort {
    private static final Comparator<Animal> COMPARATOR = (o1, o2) -> {
        final int typeCompare = o1.type().compareTo(o2.type());
        final int sexCompare = o1.sex().compareTo(o2.sex());
        return typeCompare != 0 ? typeCompare : (sexCompare != 0 ? sexCompare : o1.name().compareTo(o2.name()));
    };

    private TypeSexNameSort() {
    }

    public static List<Animal> sort(final Collection<Animal> animals) {
        return animals.stream()
            .sorted(COMPARATOR)
            .toList();
    }
}
