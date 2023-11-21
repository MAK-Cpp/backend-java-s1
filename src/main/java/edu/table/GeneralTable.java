package edu.table;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.function.BiFunction;

public class GeneralTable implements Table {
    private final String tableName;
    private final ArrayList<Column<?>> columns;
    private final ArrayList<String> columnsNames;
    private final ArrayList<String> rowsNames;

    public GeneralTable(String tableName, String keysColumnName, Column<?>... columns) {
        this.tableName = tableName;
        this.columns = new ArrayList<>(columns.length + 1);
        columnsNames = new ArrayList<>(columns.length + 1);
        columnsNames.add(keysColumnName);
        this.columns.add(new StringColumn(keysColumnName));
        for (int i = 1; i <= columns.length; i++) {
            columnsNames.add(columns[i - 1].getName());
            this.columns.add(columns[i - 1]);
        }
        rowsNames = new ArrayList<>(1);
        rowsNames.add(keysColumnName);
    }

    private int getRowID(String row) {
        int rowID = rowsNames.indexOf(row);
        if (rowID == -1) {
            for (Column<?> c : columns) {
                c.addRow();
            }
            columns.get(0).set(rowsNames.size() - 1, row);
            rowID = rowsNames.size();
            rowsNames.add(row);
        }
        return rowID - 1;
    }

    private int getColumnID(String column) {
        int columnID = columnsNames.indexOf(column);
        if (columnID == -1) {
            throw new IllegalArgumentException("error column name: " + column);
        }
        return columnID;
    }

    @Override
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
    }
}
