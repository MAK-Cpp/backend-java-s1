package edu.prettyTable.line;

import edu.prettyTable.cell.Cell;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class RandomTypedLine implements Line {
    protected ArrayList<Cell<?>> values;
    protected String columnName;

    @Override
    public int size() {
        return values.size();
    }

    public RandomTypedLine(final String columnName) {
        this.columnName = columnName;
        this.values = new ArrayList<>();
    }

    public RandomTypedLine(final String columnName, final Cell<?>... values) {
        this.columnName = columnName;
        this.values = new ArrayList<>(List.of(values));
    }

    public RandomTypedLine(final String columnName, final Object... values) {
        this.columnName = columnName;
        this.values = new ArrayList<>(values.length);
        for (Object value : values) {
            this.values.add(Cell.of(value));
        }
    }

    @Override
    public void set(int key, Object value) {
        values.get(key).set(value);
    }

    public void replace(int key, Cell<?> cell) {
        values.set(key, cell);
    }

    @Override
    public void update(int key, Object update, BiFunction<?, ?, ?> function) {
        values.get(key).update(update, function);
    }

    @Override
    public void addRow() {
        addRow(null);
    }

    @Override
    public void addRow(Object value) {
        values.add(Cell.of(value));
    }

    @Override
    public Object getValue(int key) {
        return values.get(key).get();
    }

    @Override
    public String getName() {
        return columnName;
    }
}
