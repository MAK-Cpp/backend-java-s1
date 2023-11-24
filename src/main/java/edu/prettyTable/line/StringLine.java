package edu.prettyTable.line;

import edu.prettyTable.StringBiFunction;
import java.util.function.BiFunction;

public class StringLine extends AbstractTypedLine<String> {
    public StringLine(final String columnName) {
        super(columnName);
    }

    public StringLine(String columnName, String... values) {
        super(columnName, values);
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
