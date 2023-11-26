package edu.prettyTable.line;

import edu.prettyTable.DoubleBiFunction;
import java.util.function.BiFunction;

public class DoubleLine extends AbstractTypedLine<Double> {
    public DoubleLine(String columnName) {
        super(columnName);
    }

    public DoubleLine(String columnName, Double... values) {
        super(columnName, values);
    }

    @Override
    Double cast(Object value) {
        if (!(value instanceof Double)) {
            throw new IllegalArgumentException();
        }
        return (Double) value;
    }

    @Override
    DoubleBiFunction cast(BiFunction<?, ?, ?> function) {
        if (!(function instanceof DoubleBiFunction)) {
            throw new IllegalArgumentException();
        }
        return (DoubleBiFunction) function;
    }
}
