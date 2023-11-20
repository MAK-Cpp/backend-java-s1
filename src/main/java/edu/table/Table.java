package edu.table;

import java.util.Formatter;
import java.util.function.BiFunction;

public interface Table {
    void set(String row, String column, Object value);

    void setRow(String row, Object... values);

    void update(String row, String column, Object update, BiFunction<Object, Object, Object> function);

    Object get(String row, String column);

    void markdownFormat(Formatter formatter);

    void adocFormat(Formatter formatter);
}
