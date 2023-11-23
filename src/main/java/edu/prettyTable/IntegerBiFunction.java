package edu.prettyTable;

import java.util.function.BiFunction;

@FunctionalInterface
public interface IntegerBiFunction extends BiFunction<Integer, Integer, Integer> {
    IntegerBiFunction ADD = Integer::sum;
    IntegerBiFunction SUBTRACT = (x, y) -> x - y;
    IntegerBiFunction MULTIPLY = (x, y) -> x * y;
    IntegerBiFunction DIVIDE = (x, y) -> x / y;
}
