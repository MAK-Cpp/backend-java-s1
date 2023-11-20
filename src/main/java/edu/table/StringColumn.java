package edu.table;

import java.util.ArrayList;

public class StringColumn extends AbstractColumn<String> {
    public StringColumn(final String columnName) {
        this.columnName = columnName;
        this.values = new ArrayList<>();
        this.width = columnName.length();
    }

    public void concat(int key, String value) {
        update(key, value, (x, y) -> x + y);
    }

    @Override
    String cast(Object value) {
        if (!(value instanceof String)) {
            throw new IllegalArgumentException("Wrong value '" + value + "': type must be String");
        }
        return (String) value;
    }
}
