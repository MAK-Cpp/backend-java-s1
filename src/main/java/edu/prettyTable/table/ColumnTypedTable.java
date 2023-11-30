package edu.prettyTable.table;

import edu.prettyTable.line.Line;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;

public class ColumnTypedTable extends AbstractTable {
    private final ArrayList<Line> columns;

    private static List<String> getColumnNames(Line... columns) {
        String[] ans = new String[columns.length];
        for (int i = 0; i < columns.length; i++) {
            ans[i] = columns[i].getName();
        }
        return List.of(ans);
    }

    public ColumnTypedTable(String tableName, String intersectionCellName, Line... columns) {
        super(tableName, intersectionCellName, List.of(), getColumnNames(columns));
        this.columns = new ArrayList<>(columns.length);
        Collections.addAll(this.columns, columns);
    }

    @Override
    void set(int i, int j, Object value) {
        columns.get(j).set(i, value);
    }

    @Override
    Object get(int i, int j) {
        return columns.get(j).getValue(i);
    }

    @Override
    void update(int i, int j, Object update, BiFunction<?, ?, ?> function) {
        columns.get(j).update(i, update, function);
    }

    public void addCheckedRow(Line row) {
        for (int i = 0; i < columns.size(); i++) {
            columns.get(i).addRow(row.getValue(i));
            updateColumnWidth(i, row.getValue(i));
        }
        rowsNames.add(row.getName());
        updateRowsWithIntersectionCellWidth(row.getName().length());
    }

    public void addCheckedColumn(Line column) {
        columnsNames.add(column.getName());
        columns.add(column);
        columnsWidth.add(column.getName().length());
        int columnID = columns.size() - 1;
        for (int i = 0; i < column.size(); i++) {
            updateColumnWidth(columnID, column.getValue(i));
        }
    }
}
