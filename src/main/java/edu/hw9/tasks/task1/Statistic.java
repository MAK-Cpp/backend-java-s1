package edu.hw9.tasks.task1;

import java.util.Arrays;
import java.util.Objects;

public class Statistic {
    private final String name;
    private Double sum = 0.;
    private AverageValue average;
    private Double max = null;
    private Double min = null;
    private static final double EPS = 1e-7;

    public Statistic(String name) {
        this.name = name;
        this.average = new AverageValue();
    }

    public Statistic(String name, double sum, double average, double max, double min) {
        this(name, sum, new AverageValue(average, 1), max, min);
    }

    public Statistic(String name, double sum, AverageValue average, double max, double min) {
        this.name = name;
        this.sum = sum;
        this.average = average;
        this.max = max;
        this.min = min;
    }

    public Statistic merge(Statistic statistic) {
        if (!name.equals(statistic.name)) {
            throw new IllegalArgumentException("cannot merge different statistics!");
        }
        return new Statistic(
            name,
            sum + statistic.sum,
            average.add(statistic.average),
            (max == null ? statistic.max : Math.max(max, statistic.max)),
            (min == null ? statistic.min : Math.min(min, statistic.min))
        );
    }

    public void addToSum(double... values) {
        sum += Arrays.stream(values).sum();
    }

    public void addToAverage(double... values) {
        average = average.add(values);
    }

    public void updateMax(double... values) {
        for (double value : values) {
            max = (max == null ? value : Math.max(max, value));
        }
    }

    public void updateMin(double... values) {
        for (double value : values) {
            min = (min == null ? value : Math.min(min, value));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Statistic statistic = (Statistic) o;
        return Objects.equals(name, statistic.name)
            && (Math.abs(sum - statistic.sum) < EPS)
            && (Math.abs(average.value() - statistic.average.value()) < EPS)
            && (Math.abs(max - statistic.max) < EPS)
            && (Math.abs(min - statistic.min) < EPS);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, sum, average.value(), max, min);
    }

    @Override
    public String toString() {
        return "Statistic{"
            + "name='" + name + '\'' + ", sum=" + sum + ", average=" + average + ", max=" + max + ", min=" + min + '}';
    }
}
