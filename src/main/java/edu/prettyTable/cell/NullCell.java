package edu.prettyTable.cell;

import java.util.function.BiFunction;

public class NullCell extends AbstractCell<Object> {
    private static final BiFunction<Object, Object, Object> NULL_FUNCTION = (x, y) -> null;

    @Override
    Object cast(Object value) {
        return null;
    }

    @Override
    BiFunction<Object, Object, Object> cast(BiFunction<?, ?, ?> function) {
        return NULL_FUNCTION;
    }

    public NullCell() {
        super(null);
    }
}
