package edu.prettyTable.line;

import java.util.function.BiFunction;

public interface Line {
    void set(int key, Object value);

    void swap(int keyA, int keyB);

    void update(int key, Object update, BiFunction<?, ?, ?> function);

    void addRow();

    void addRow(Object value);

    Object getValue(int key);

    String getName();

    int size();
}
