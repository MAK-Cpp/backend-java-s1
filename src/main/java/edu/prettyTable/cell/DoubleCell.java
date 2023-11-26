package edu.prettyTable.cell;

import edu.prettyTable.DoubleBiFunction;
import java.util.function.BiFunction;

public class DoubleCell extends AbstractCell<Double> {
    public DoubleCell(Double value) {
        super(value);
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
