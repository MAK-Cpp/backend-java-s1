package edu.prettyTable.column;

import java.util.ArrayList;
import java.util.function.BiFunction;

public abstract class AbstractColumn<T> implements Column<T> {
    protected ArrayList<T> values;
    protected String columnName;

    abstract T cast(Object value);

    abstract BiFunction<T, T, T> cast(BiFunction<?, ?, ?> value);

    public AbstractColumn(final String columnName) {
        this.columnName = columnName;
        this.values = new ArrayList<>();
    }

    @Override
    public void set(int key, Object value) {
        T castedValue = cast(value);
        values.set(key, castedValue);
    }

    @Override
    public void update(int key, Object update, BiFunction<?, ?, ?> function) {
        T castedUpdate = cast(update);
        BiFunction<T, T, T> castedFunction = cast(function);
        values.set(key, castedFunction.apply(values.get(key), castedUpdate));
    }

    @Override
    public void addRow() {
        values.add(null);
    }

    @Override
    public void addRow(Object value) {
        values.add(cast(value));
    }

    @Override final public T getValue(int key) {
        return values.get(key);
    }

    @Override final public String getName() {
        return columnName;
    }
}
