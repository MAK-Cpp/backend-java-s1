package edu.prettyTable.table;

import edu.prettyTable.column.Column;
import edu.prettyTable.table.AbstractTable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;

public class TypedColumnsTable extends AbstractTable {
    private final ArrayList<Column<?>> columns;

    private static List<String> getColumnNames(Column<?>... columns) {
        String[] ans = new String[columns.length];
        for (int i = 0; i < columns.length; i++) {
            ans[i] = columns[i].getName();
        }
        return List.of(ans);
    }

    public TypedColumnsTable(String tableName, String intersectionCellName, Column<?>... columns) {
        super(tableName, intersectionCellName, List.of(), getColumnNames(columns));
        this.columns = new ArrayList<>(columns.length);
        Collections.addAll(this.columns, columns);
    }

    private int getOrAddRowID(String row) {
        int rowID = rowsNames.indexOf(row);
        if (rowID == -1) {
            for (Column<?> c : columns) {
                c.addRow();
            }
            rowID = rowsNames.size();
            rowsNames.add(row);
        }
        return rowID;
    }

    private int getOrAddColumnID(Column<?> column) {
        int columnID = columnsNames.indexOf(column.getName());
        if (columnID == -1) {
            columns.add(column);
            columnID = columnsNames.size();
            columnsNames.add(column.getName());
        }
        return columnID;
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

    public void addRow(final String row) {
        for (Column<?> c : columns) {
            c.addRow();
        }
        rowsNames.add(row);
        updateRowsWithIntersectionCellWidth(row.length());
    }

    public void addRow(final String row, final Object... values) {
        for (int i = 0; i < columns.size(); i++) {
            columns.get(i).addRow(values[i]);
            updateColumnWidth(i, values[i]);
        }
        rowsNames.add(row);
        updateRowsWithIntersectionCellWidth(row.length());
    }
/*    @Override
    public void set(String row, String column, Object value) {
        int rowID = getRowID(row);
        int columnID = getColumnID(column);
        columns.get(columnID).set(rowID, value);
    }

    @Override
    public void setRow(String row, Object... values) {
        for (int i = 1; i < columnsNames.size(); i++) {
            set(row, columnsNames.get(i), values[i - 1]);
        }
    }

    @Override
    public void update(String row, String column, Object update, BiFunction<?, ?, ?> function) {
        int rowID = getRowID(row);
        int columnID = getColumnID(column);
        columns.get(columnID).update(rowID, update, function);
    }

    @Override
    public void updateOrSet(String row, String column, Object value, BiFunction<?, ?, ?> function) {
        if (contains(row, column)) {
            update(row, column, value, function);
        } else {
            set(row, column, value);
        }
    }

    @Override
    public Object get(String row, String column) {
        int rowID = getRowID(row);
        int columnID = getColumnID(column);
        return columns.get(columnID).getValue(rowID);
    }

    @Override
    public boolean contains(String row, String column) {
        return containsRow(row) && containsColumn(column);
    }

    @Override
    public boolean containsRow(String row) {
        return rowsNames.contains(row);
    }

    @Override
    public boolean containsColumn(String column) {
        return columnsNames.contains(column);
    }

    private static String centerString(int width, String s) {
        return String.format("%-" + width + "s", String.format("%" + (s.length() + (width - s.length()) / 2) + "s", s));
    }

    private void printTitle(Formatter formatter, String format) {
        formatter.format(format, tableName);
    }

    private void printColumnNames(Formatter formatter, String format) {
        String[] columnNamesArray = new String[columnsNames.size()];
        for (int i = 0; i < columnsNames.size(); i++) {
            columnNamesArray[i] = centerString(columns.get(i).getWidth(), columnsNames.get(i));
        }
        formatter.format(format, columnNamesArray);
    }

    private void printValues(Formatter formatter, String format) {
        for (int i = 1; i < rowsNames.size(); i++) {
            String[] output = new String[columns.size()];
            output[0] = centerString(columns.get(0).getWidth(), rowsNames.get(i));
            for (int j = 1; j < columnsNames.size(); j++) {
                Column<?> column = columns.get(j);
                output[j] = centerString(
                    column.getWidth(),
                    (column.getValue(i - 1) == null ? "" : column.getValue(i - 1).toString())
                );
            }
            formatter.format(format, output);
        }
    }

    @SuppressWarnings("checkstyle:MultipleStringLiterals")
    private void adocFormat(Formatter formatter) {
        printTitle(formatter, "=== %s\n");

        StringBuilder separatorBuilder = new StringBuilder().append("|");
        StringBuilder formatBuilder = new StringBuilder();
        for (Column<?> column : columns) {
            separatorBuilder.append("=".repeat(column.getWidth() + 2));
            formatBuilder.append("| %").append(column.getWidth()).append("s");
        }
        separatorBuilder.append('\n');
        formatBuilder.append("\n");
        String format = formatBuilder.toString();
        String separator = separatorBuilder.toString();

        formatter.format(separator);
        printColumnNames(formatter, format);
        printValues(formatter, format);
        formatter.format(separator);
    }

    private void markdownFormat(Formatter formatter) {
        printTitle(formatter, "### %s\n");

        StringBuilder separatorBuilder = new StringBuilder().append("|");
        StringBuilder formatBuilder = new StringBuilder();
        for (Column<?> column : columns) {
            separatorBuilder.append(":").append("-".repeat(column.getWidth())).append(":|");
            formatBuilder.append("| %").append(column.getWidth()).append("s ");
        }
        separatorBuilder.append('\n');
        formatBuilder.append("|\n");
        String format = formatBuilder.toString();
        String separator = separatorBuilder.toString();

        printColumnNames(formatter, format);
        formatter.format(separator);
        printValues(formatter, format);
    }

    @Override
    public void format(Format format, Formatter formatter) {
        switch (format) {
            case MARKDOWN -> markdownFormat(formatter);
            case ADOC -> adocFormat(formatter);
            default -> throw new IllegalArgumentException("Unsupported format: " + format);
        }
    }*/
}
