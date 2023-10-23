package edu.hw3.tasks.task7;

import java.util.Comparator;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

public class TreeNullComparator<T> implements Comparator<T> {
    @Override
    public int compare(T o1, T o2) {
        return 0;
    }

    @Override
    public Comparator<T> reversed() {
        return Comparator.super.reversed();
    }

    @Override
    public Comparator<T> thenComparing(Comparator<? super T> other) {
        return Comparator.super.thenComparing(other);
    }

    @Override
    public <U> Comparator<T> thenComparing(
        Function<? super T, ? extends U> keyExtractor,
        Comparator<? super U> keyComparator
    ) {
        return Comparator.super.thenComparing(keyExtractor, keyComparator);
    }

    @Override
    public <U extends Comparable<? super U>> Comparator<T> thenComparing(Function<? super T, ? extends U> keyExtractor) {
        return Comparator.super.thenComparing(keyExtractor);
    }

    @Override
    public Comparator<T> thenComparingInt(ToIntFunction<? super T> keyExtractor) {
        return Comparator.super.thenComparingInt(keyExtractor);
    }

    @Override
    public Comparator<T> thenComparingLong(ToLongFunction<? super T> keyExtractor) {
        return Comparator.super.thenComparingLong(keyExtractor);
    }

    @Override
    public Comparator<T> thenComparingDouble(ToDoubleFunction<? super T> keyExtractor) {
        return Comparator.super.thenComparingDouble(keyExtractor);
    }
}
