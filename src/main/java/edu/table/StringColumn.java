package edu.table;

import java.util.ArrayList;
import java.util.function.BiFunction;

public class StringColumn extends AbstractColumn<String> {
    public static final StringBiFunction CONCAT = (x, y) -> x + y;

    public StringColumn(final String columnName) {
        this.columnName = columnName;
        this.values = new ArrayList<>();
        this.width = columnName.length();
    }

    @Override
    String cast(Object value) {
        if (!(value instanceof String)) {
            throw new IllegalArgumentException("Wrong value '" + value + "': type must be String");
        }
        return (String) value;
    }

    @Override
    StringBiFunction cast(BiFunction<?, ?, ?> value) {
        if (!(value instanceof StringBiFunction)) {
            throw new IllegalArgumentException();
        }
        return (StringBiFunction) value;
    }
}
