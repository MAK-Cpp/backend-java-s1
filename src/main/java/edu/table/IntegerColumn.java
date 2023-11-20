package edu.table;

import java.util.ArrayList;

public class IntegerColumn extends AbstractColumn<Integer> {
    public IntegerColumn(final String columnName) {
        this.columnName = columnName;
        this.values = new ArrayList<>();
        this.width = columnName.length();
    }

    public void add(int key, int value) {
        update(key, value, Integer::sum);
    }

    public void subtract(int key, int value) {
        update(key, value, (x, y) -> x - y);
    }

    public void multiply(int key, int value) {
        update(key, value, (x, y) -> x * y);
    }

    public void divide(int key, int value) {
        update(key, value, (x, y) -> x / y);
    }

    @Override
    Integer cast(Object value) {
        if (!(value instanceof Integer)) {
            throw new IllegalArgumentException("Wrong value '" + value + "': type must be Integer");
        }
        return (Integer) value;
    }
}
