package edu.prettyTable.cell;

import java.util.function.BiFunction;

public interface Cell<T> {
    void set(Object value);

    void update(Object value, BiFunction<?, ?, ?> function);

    T get();

    static Cell<?> of(Object value) {
        if (value == null) {
            return new NullCell();
        }
        return switch (value) {
            case String s -> new StringCell(s);
            case Integer i -> new IntegerCell(i);
            case Double d -> new DoubleCell(d);
            default -> new ObjectCell(value);
        };
    }
}
