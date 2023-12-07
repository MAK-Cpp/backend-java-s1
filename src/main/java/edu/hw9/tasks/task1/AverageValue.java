package edu.hw9.tasks.task1;

public class AverageValue {
    private double sum;
    private int count;

    public AverageValue() {
        sum = 0;
        count = 0;
    }

    public AverageValue(double sum, int count) {
        this.sum = sum;
        this.count = count;
    }

    public AverageValue(double[] values) {
        sum = 0;
        for (double value : values) {
            sum += value;
        }
        count = values.length;
    }

    public AverageValue add(AverageValue other) {
        return new AverageValue(sum + other.sum, count + other.count);
    }

    public double value() {
        return count == 0 ? 0.0 : sum / count;
    }

    @Override
    public String toString() {
        return Double.toString(value());
    }
}
