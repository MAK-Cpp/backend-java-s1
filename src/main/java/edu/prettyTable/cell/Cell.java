package edu.prettyTable.cell;

import java.util.function.BiFunction;

public interface Cell<T> {
    void set(Object value);

    void update(Object value, BiFunction<?, ?, ?> function);

    T get();
}
