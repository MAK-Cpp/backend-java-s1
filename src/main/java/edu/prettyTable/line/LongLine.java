package edu.prettyTable.line;

import edu.prettyTable.LongBiFunction;
import java.util.function.BiFunction;

public class LongLine extends AbstractTypedLine<Long> {
    public LongLine(String columnName) {
        super(columnName);
    }

    public LongLine(String columnName, Long... values) {
        super(columnName, values);
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
