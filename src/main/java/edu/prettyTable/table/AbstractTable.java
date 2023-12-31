package edu.prettyTable.table;

import edu.prettyTable.Format;
import edu.prettyTable.line.Line;
import edu.prettyTable.line.RandomTypedLine;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.function.BiFunction;
import static edu.prettyTable.Format.TXT;

/*package-private*/ abstract class AbstractTable implements Table {
    /*
        Table name

        intersection cell | column names -->
        ------------------+--------------------
               rows       |
               names      |
                ||        |
                \/        |
                          |
        */
    protected final String tableName;
    protected ArrayList<String> rowsNames;
    protected final ArrayList<String> columnsNames;
    protected final ArrayList<Integer> columnsWidth;
    protected final String intersectionCellName;
    protected Integer rowsWithIntersectionCellWidth;

    abstract void set(int i, int j, Object value);

    abstract Object get(int i, int j);

    abstract void update(int i, int j, Object update, BiFunction<?, ?, ?> function);

    abstract void addCheckedColumn(Line column);

    abstract void addCheckedRow(Line row);

    AbstractTable(
        String tableName,
        String intersectionCellName,
        List<String> rowsNames,
        List<String> columnsNames
    ) {
        this.tableName = tableName;
        this.intersectionCellName = intersectionCellName;
        this.rowsNames = new ArrayList<>(rowsNames);
        this.columnsNames = new ArrayList<>(columnsNames);
        rowsWithIntersectionCellWidth = intersectionCellName.length();
        columnsWidth = new ArrayList<>(columnsNames.size());
        for (String name : columnsNames) {
            columnsWidth.add(name.length());
        }
    }

    protected int getRowID(String row) {
        int rowID = rowsNames.indexOf(row);
        if (rowID == -1) {
            throw new IllegalArgumentException("error row name: " + row);
        }
        return rowID;
    }

    protected int getColumnID(String column) {
        int columnID = columnsNames.indexOf(column);
        if (columnID == -1) {
            throw new IllegalArgumentException("error column name: " + column);
        }
        return columnID;
    }

    @Override
    public final boolean contains(String row, String column) {
        return containsRow(row) && containsColumn(column);
    }

    @Override
    public final boolean containsRow(String row) {
        return rowsNames.contains(row);
    }

    @Override
    public final boolean containsColumn(String column) {
        return columnsNames.contains(column);
    }

    @Override
    public final void addRow(String row, Object... values) {
        addRow(new RandomTypedLine(row, values));
    }

    @Override
    public final void addColumn(String column, Object... values) {
        addColumn(new RandomTypedLine(column, values));
    }

    @SuppressWarnings("checkstyle:MultipleStringLiterals")
    @Override
    public final void addRow(Line row) {
        if (row.size() != columnsNames.size()) {
            throw new IllegalArgumentException("wrong row size: " + row.size() + ", it must be " + columnsNames.size());
        } else if (containsRow(row.getName())) {
            throw new IllegalArgumentException("there is already row " + row.getName() + " in table");
        }
        addCheckedRow(row);
    }

    @Override
    public final void addColumn(Line column) {
        if (column.size() != rowsNames.size()) {
            throw new IllegalArgumentException(
                "wrong column size: " + column.size() + ", it must be " + rowsNames.size());
        } else if (containsColumn(column.getName())) {
            throw new IllegalArgumentException();
        }
        addCheckedColumn(column);
    }

    @Override
    public final Object get(String row, String column) {
        return get(getRowID(row), getColumnID(column));
    }

    protected void updateColumnWidth(int columnID, int width) {
        int newWidth = Math.max(columnsWidth.get(columnID), width);
        columnsWidth.set(columnID, newWidth);
    }

    protected void updateColumnWidth(int columnID, Object value) {
        if (value != null) {
            updateColumnWidth(columnID, value.toString().length());
        }
    }

    protected void updateRowsWithIntersectionCellWidth(int width) {
        rowsWithIntersectionCellWidth = Math.max(rowsWithIntersectionCellWidth, width);
    }

    @Override
    public final void setColumn(String column, Object... values) {
        int columnID = getColumnID(column);
        for (int i = 0; i < rowsNames.size(); i++) {
            set(i, columnID, values[i]);
        }
    }

    @Override
    public final void setRow(String row, Object... values) {
        int rowID = getRowID(row);
        for (int j = 0; j < columnsNames.size(); j++) {
            set(rowID, j, values[j]);
        }
    }

    @Override
    public final void set(String row, String column, Object value) {
        int rowID = getRowID(row);
        int columnID = getColumnID(column);
        set(rowID, columnID, value);
        updateColumnWidth(columnID, value);
    }

    @Override
    public final void update(String row, String column, Object update, BiFunction<?, ?, ?> function) {
        int rowID = getRowID(row);
        int columnID = getColumnID(column);
        update(rowID, columnID, update, function);
        updateColumnWidth(columnID, get(rowID, columnID));
    }

    @Override
    public final void format(Format format, Formatter formatter) {
        switch (format) {
            case MARKDOWN -> markdownFormat(formatter);
            case ADOC -> adocFormat(formatter);
            case TXT -> txtFormat(formatter);
            default -> throw new IllegalArgumentException("Unsupported format: " + format);
        }
    }

    @Override
    public final String toString() {
        Formatter formatter = new Formatter();
        format(TXT, formatter);
        return formatter.toString();
    }

    private static String centerString(int width, String s) {
        return String.format("%-" + width + "s", String.format("%" + (s.length() + (width - s.length()) / 2) + "s", s));
    }

    private void printTitle(Formatter formatter, String format) {
        formatter.format(format, tableName);
    }

    private void printColumnNames(Formatter formatter, String format) {
        String[] columnNamesArray = new String[columnsNames.size() + 1];
        columnNamesArray[0] = centerString(rowsWithIntersectionCellWidth, intersectionCellName);
        for (int i = 0; i < columnsNames.size(); i++) {
            columnNamesArray[i + 1] = centerString(columnsWidth.get(i), columnsNames.get(i));
        }
        formatter.format(format, columnNamesArray);
    }

    private void printValues(Formatter formatter, String format) {
        for (int i = 0; i < rowsNames.size(); i++) {
            final String[] output = new String[columnsWidth.size() + 1];
            output[0] = centerString(rowsWithIntersectionCellWidth, rowsNames.get(i));
            for (int j = 0; j < columnsNames.size(); j++) {
                final Object value = get(i, j);
                output[j + 1] = centerString(columnsWidth.get(j), (value == null ? "" : value.toString()));
            }
            formatter.format(format, output);
        }
    }

    @SuppressWarnings("checkstyle:MultipleStringLiterals")
    private void adocFormat(Formatter formatter) {
        final StringBuilder separatorBuilder =
            new StringBuilder().append("|").append("=".repeat(rowsWithIntersectionCellWidth + 2));
        final StringBuilder formatBuilder =
            new StringBuilder().append("| %").append(rowsWithIntersectionCellWidth).append("s");
        for (int width : columnsWidth) {
            separatorBuilder.append("=".repeat(width + 2));
            formatBuilder.append("| %").append(width).append("s");
        }
        separatorBuilder.append('\n');
        formatBuilder.append("\n");
        final String format = formatBuilder.toString();
        final String separator = separatorBuilder.toString();

        printTitle(formatter, "=== %s\n");
        formatter.format(separator);
        printColumnNames(formatter, format);
        printValues(formatter, format);
        formatter.format(separator);
    }

    private void markdownFormat(Formatter formatter) {
        final StringBuilder separatorBuilder =
            new StringBuilder().append("|:").append("-".repeat(rowsWithIntersectionCellWidth)).append(":|");
        final StringBuilder formatBuilder =
            new StringBuilder().append("| %").append(rowsWithIntersectionCellWidth).append("s ");
        for (int width : columnsWidth) {
            separatorBuilder.append(":").append("-".repeat(width)).append(":|");
            formatBuilder.append("| %").append(width).append("s ");
        }
        separatorBuilder.append('\n');
        formatBuilder.append("|\n");
        final String format = formatBuilder.toString();
        final String separator = separatorBuilder.toString();

        printTitle(formatter, "### %s\n");
        printColumnNames(formatter, format);
        formatter.format(separator);
        printValues(formatter, format);
    }

    @SuppressWarnings("checkstyle:MultipleStringLiterals")
    private void txtFormat(Formatter formatter) {
        final StringBuilder separatorBuilder =
            new StringBuilder().append("[]").append("=".repeat(rowsWithIntersectionCellWidth + 2));
        final StringBuilder formatBuilder =
            new StringBuilder().append("|| %").append(rowsWithIntersectionCellWidth).append("s ");
        for (int width : columnsWidth) {
            separatorBuilder.append("[]").append("=".repeat(width + 2));
            formatBuilder.append("|| %").append(width).append("s ");
        }
        separatorBuilder.append("[]\n");
        formatBuilder.append("||\n");
        String format = formatBuilder.toString();
        String separator = separatorBuilder.toString();

        final int tableWidth = separator.length() - 1;
        final String titleSeparator = centerString(tableWidth, "." + ".".repeat(tableName.length() + 2) + ".") + '\n';
        final String belowTitleSeparator = centerString(tableWidth, "˙".repeat(tableName.length() + 4)) + '\n';
        final String titleFormat = titleSeparator
            + centerString(tableWidth, ": " + tableName + " :") + "\n"
            + belowTitleSeparator;
        if (titleSeparator.length() > tableWidth) {
            final int deltaLength = (titleSeparator.length() - 1 - tableWidth) / 2;
            format = " ".repeat(deltaLength) + format;
            separator = " ".repeat(deltaLength) + separator;
        }
        printTitle(formatter, titleFormat);
        formatter.format(separator);
        printColumnNames(formatter, format);
        formatter.format(separator);
        printValues(formatter, format);
        formatter.format(separator);
    }
}
