package edu.prettyTable.column;

import edu.prettyTable.IntegerBiFunction;
import edu.prettyTable.column.AbstractColumn;
import java.util.function.BiFunction;

public class IntegerColumn extends AbstractColumn<Integer> {
    public IntegerColumn(final String columnName) {
        super(columnName);
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
