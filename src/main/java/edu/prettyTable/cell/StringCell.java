package edu.prettyTable.cell;

import java.util.function.BiFunction;

public class StringCell extends AbstractCell<String> {
    public StringCell(String value) {
        super(value);
    }

    @Override
    String cast(Object value) {
        return null;
    }

    @Override
    BiFunction<String, String, String> cast(BiFunction<?, ?, ?> function) {
        return null;
    }
}
