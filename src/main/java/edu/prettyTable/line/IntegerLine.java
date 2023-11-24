package edu.prettyTable.line;

import edu.prettyTable.IntegerBiFunction;
import java.util.function.BiFunction;

public class IntegerLine extends AbstractTypedLine<Integer> {
    public IntegerLine(final String columnName) {
        super(columnName);
    }

    public IntegerLine(String columnName, Integer... values) {
        super(columnName, values);
    }

    @Override
    Integer cast(Object value) {
        if (!(value instanceof Integer)) {
            throw new IllegalArgumentException("Wrong value '" + value + "': type must be Integer");
        }
        return (Integer) value;
    }

    @Override
    IntegerBiFunction cast(BiFunction<?, ?, ?> value) {
        if (!(value instanceof IntegerBiFunction)) {
            throw new IllegalArgumentException();
        }
        return (IntegerBiFunction) value;
    }
}
