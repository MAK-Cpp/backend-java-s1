package edu.hw3.tasks.task7;

import java.util.Comparator;

public class NullComparator<T> implements Comparator<T> {
    private final Comparator<T> compareWithoutNull;

    public NullComparator(final Comparator<T> comparatorWithoutNull) {
        this.compareWithoutNull = comparatorWithoutNull;
    }

    @Override
    public final int compare(T o1, T o2) {
        if (o1 == o2) {
            return 0;
        } else if (o1 == null) {
            return -1;
        } else if (o2 == null) {
            return 1;
        }
        return compareWithoutNull.compare(o1, o2);
    }
}
