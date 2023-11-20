package edu.table;

import java.util.function.BiFunction;

public interface Column<T> {
    void set(int key, Object value);

    void update(int key, Object update, BiFunction<T, T, T> function);

    void addRow();

    T getValue(int key);

    int getWidth();

    String getName();
}
