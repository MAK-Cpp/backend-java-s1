package edu.prettyTable.cell;

import edu.prettyTable.IntegerBiFunction;
import java.util.function.BiFunction;

public class IntegerCell extends AbstractCell<Integer> {
    public IntegerCell(Integer value) {
        super(value);
    }

    @Override
    Integer cast(Object value) {
        if (!(value instanceof Integer)) {
            throw new IllegalArgumentException("value " + value + " is not Integer");
        }
        return (Integer) value;
    }

    @Override
    BiFunction<Integer, Integer, Integer> cast(BiFunction<?, ?, ?> function) {
        if (!(function instanceof IntegerBiFunction)) {
            throw new IllegalArgumentException("function " + function + " is not instance of integer binary function");
        }
        return (IntegerBiFunction) function;
    }
}
