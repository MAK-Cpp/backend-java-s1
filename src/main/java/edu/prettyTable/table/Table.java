package edu.prettyTable.table;

import edu.prettyTable.Format;
import edu.prettyTable.line.Line;
import java.util.Formatter;
import java.util.function.BiFunction;

public interface Table {
    void set(String row, String column, Object value);

    void setRow(String row, Object... values);

    void addRow(Line row);

    void addRow(String row, Object... values);

    void setColumn(String column, Object... values);

    void addColumn(Line column);

    void addColumn(String column, Object... values);

    void update(String row, String column, Object update, BiFunction<?, ?, ?> function);

    Object get(String row, String column);

    boolean contains(String row, String column);

    boolean containsRow(String row);

    boolean containsColumn(String column);

    void sortRows();

    void format(Format format, Formatter formatter);
}
