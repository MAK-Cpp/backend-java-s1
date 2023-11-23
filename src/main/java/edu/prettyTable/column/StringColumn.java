package edu.prettyTable.column;

import edu.prettyTable.StringBiFunction;
import java.util.function.BiFunction;

public class StringColumn extends AbstractColumn<String> {
    public StringColumn(final String columnName) {
        super(columnName);
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
