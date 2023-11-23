package edu.prettyTable.table;

import edu.prettyTable.cell.Cell;
import edu.prettyTable.cell.NullCell;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class RandomTypeTable extends AbstractTable {
    private final ArrayList<ArrayList<Cell<?>>> values;

    public RandomTypeTable(
        String tableName,
        String intersectionCellName,
        List<String> rowsNames,
        List<String> columnsNames,
        ArrayList<ArrayList<Cell<?>>> values
    ) {
        super(tableName, intersectionCellName, rowsNames, columnsNames);
        this.values = values;
    }

    public RandomTypeTable(
        String tableName,
        String intersectionCellName,
        List<String> rowsNames,
        List<String> columnsNames
    ) {
        super(tableName, intersectionCellName, rowsNames, columnsNames);
        this.values = new ArrayList<>(rowsNames.size());
        for (int i = 0; i < rowsNames.size(); i++) {
            this.values.add(new ArrayList<>(columnsNames.size()));
            for (int j = 0; j < columnsNames.size(); j++) {
                this.values.get(i).add(new NullCell());
            }
        }
    }

    @Override
    protected void set(int i, int j, Object value) {
        values.get(i).get(j).set(value);
    }

    public void setRow(String row, Cell<?>... values) {
        int rowID = getRowID(row);
        for (int j = 0; j < columnsNames.size(); j++) {
            replaceCell(rowID, j, values[j]);
        }
    }

    public void setColumn(String column, Cell<?>... values) {
        int columnID = getColumnID(column);
        for (int i = 0; i < rowsNames.size(); i++) {
            replaceCell(i, columnID, values[i]);
        }
    }

    private void replaceCell(int i, int j, Cell<?> cell) {
        values.get(i).set(j, cell);
        updateColumnWidth(j, cell.get().toString().length());
    }

    public void replaceCell(String row, String column, Cell<?> cell) {
        replaceCell(getRowID(row), getColumnID(column), cell);
    }

    @Override
    protected Object get(int i, int j) {
        return values.get(i).get(j).get();
    }

    @Override
    protected void update(int i, int j, Object update, BiFunction<?, ?, ?> function) {
        values.get(i).get(j).update(update, function);
    }

    public void addRow(String row) {
        if (containsRow(row)) {
            throw new IllegalArgumentException("row '" + row + "' already exist");
        }
        values.add(new ArrayList<>(columnsNames.size()));
        for (int i = rowsNames.size(), j = 0; j < columnsNames.size(); j++) {
            values.get(i).set(j, new NullCell());
        }
        rowsNames.add(row);
        updateRowsWithIntersectionCellWidth(row.length());
    }

    public void addColumn(String column) {
        if (containsColumn(column)) {
            throw new IllegalArgumentException("column '" + column + "' already exist");
        }
        columnsNames.add(column);
        columnsWidth.add(column.length());
        for (int i = 0; i < rowsNames.size(); i++) {
            values.get(i).add(new NullCell());
        }
    }
}
