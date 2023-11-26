package edu.prettyTable.cell;

import edu.prettyTable.LongBiFunction;
import java.util.function.BiFunction;

public class LongCell extends AbstractCell<Long> {
    public LongCell(Long value) {
        super(value);
    }

    @Override
    Long cast(Object value) {
        if (!(value instanceof Long)) {
            throw new IllegalArgumentException();
        }
        return (Long) value;
    }

    @Override
    LongBiFunction cast(BiFunction<?, ?, ?> function) {
        if (!(function instanceof LongBiFunction)) {
            throw new IllegalArgumentException();
        }
        return (LongBiFunction) function;
    }
}
