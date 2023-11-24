package edu.prettyTable.cell;

import edu.prettyTable.ObjectFunction;
import java.util.function.BiFunction;

public class ObjectCell extends AbstractCell<Object> {
    public ObjectCell(Object value) {
        super(value);
    }

    @Override
    Object cast(Object value) {
        return value;
    }

    @Override
    ObjectFunction cast(BiFunction<?, ?, ?> function) {
        if (!(function instanceof ObjectFunction)) {
            throw new IllegalArgumentException();
        }
        return (ObjectFunction) function;
    }
}
