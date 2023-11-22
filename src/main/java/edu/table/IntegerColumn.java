package edu.table;

import java.util.ArrayList;
import java.util.function.BiFunction;

public class IntegerColumn extends AbstractColumn<Integer> {
    public static final IntegerBiFunction ADD = Integer::sum;
    public static final IntegerBiFunction SUBTRACT = (x, y) -> x - y;
    public static final IntegerBiFunction MULTIPLY = (x, y) -> x * y;
    public static final IntegerBiFunction DIVIDE = (x, y) -> x / y;

    public IntegerColumn(final String columnName) {
        this.columnName = columnName;
        this.values = new ArrayList<>();
        this.width = columnName.length();
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
