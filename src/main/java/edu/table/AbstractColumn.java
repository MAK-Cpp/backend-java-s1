package edu.table;

import java.util.ArrayList;
import java.util.function.BiFunction;

public abstract class AbstractColumn<T> implements Column<T> {
    protected ArrayList<T> values;
    protected String columnName;
    protected int width;

    abstract T cast(Object value);

    abstract BiFunction<T, T, T> cast(BiFunction<?, ?, ?> value);

    @Override
    public void set(int key, Object value) {
        T castedValue = cast(value);
        values.set(key, castedValue);
        width = Math.max(width, castedValue.toString().length());
    }

    @Override
    public void update(int key, Object update, BiFunction<?, ?, ?> function) {
        T castedUpdate = cast(update);
        BiFunction<T, T, T> castedFunction = cast(function);
        values.set(key, castedFunction.apply(values.get(key), castedUpdate));
        width = Math.max(width, castedUpdate.toString().length());
    }

    @Override
    public void addRow() {
        values.add(null);
        width = Math.max(width, 4);
    }

    @Override final public int getWidth() {
        return width;
    }

    @Override final public T getValue(int key) {
        return values.get(key);
    }

    @Override final public String getName() {
        return columnName;
    }
}
