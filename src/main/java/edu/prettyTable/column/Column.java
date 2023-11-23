package edu.prettyTable.column;

import java.util.function.BiFunction;

public interface Column<T> {
    void set(int key, Object value);

    void update(int key, Object update, BiFunction<?, ?, ?> function);

    void addRow();

    void addRow(Object value);

    T getValue(int key);

    String getName();
}
