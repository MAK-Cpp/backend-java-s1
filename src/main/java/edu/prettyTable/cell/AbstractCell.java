package edu.prettyTable.cell;

import java.util.function.BiFunction;

public abstract class AbstractCell<T> implements Cell<T> {
    private T value;

    abstract T cast(Object value);

    abstract BiFunction<T, T, T> cast(BiFunction<?, ?, ?> function);

    public AbstractCell(T value) {
        this.value = value;
    }

    @Override
    public void update(Object value, BiFunction<?, ?, ?> function) {
        set(cast(function).apply(get(), cast(value)));
    }

    @Override
    public void set(Object value) {
        this.value = cast(value);
    }

    @Override
    public T get() {
        return value;
    }
}
