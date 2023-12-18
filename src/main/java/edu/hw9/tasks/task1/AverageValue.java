package edu.hw9.tasks.task1;

import java.util.Arrays;

public class AverageValue {
    private final double sum;
    private final int count;

    public AverageValue() {
        sum = 0;
        count = 0;
    }

    public AverageValue(double sum, int count) {
        this.sum = sum;
        this.count = count;
    }

    public AverageValue(double[] values) {
        sum = Arrays.stream(values).sum();
        count = values.length;
    }

    public AverageValue add(AverageValue other) {
        return new AverageValue(
            sum + other.sum,
            count + other.count
        );
    }

    public AverageValue add(double... values) {
        return add(new AverageValue(values));
    }

    public double value() {
        return count == 0 ? 0.0 : sum / count;
    }

    @Override
    public String toString() {
        return Double.toString(value());
    }
}
