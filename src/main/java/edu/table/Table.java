package edu.table;

import java.util.Formatter;
import java.util.function.BiFunction;

public interface Table {
    void set(String row, String column, Object value);

    void setRow(String row, Object... values);

    void update(String row, String column, Object update, BiFunction<?, ?, ?> function);

    void updateOrSet(String row, String column, Object value, BiFunction<?, ?, ?> function);

    Object get(String row, String column);

    boolean contains(String row, String column);

    boolean containsRow(String row);

    boolean containsColumn(String column);

    void format(Format format, Formatter formatter);
}
