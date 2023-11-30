package edu.prettyTable;

import java.util.function.BiFunction;

@FunctionalInterface
public interface StringBiFunction extends BiFunction<String, String, String> {
    StringBiFunction CONCAT = (x, y) -> x + y;
}
