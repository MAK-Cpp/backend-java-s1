package edu.hw9.tasks.task1;

import java.util.Objects;

public record Statistic(String name, Double sum, Double average, Double max, Double min) {
    private static final double EPS = 1e-7;

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Statistic statistic = (Statistic) o;
        return Objects.equals(name, statistic.name)
            && (Math.abs(sum - statistic.sum) < EPS)
            && (Math.abs(average - statistic.average) < EPS)
            && (Math.abs(max - statistic.max) < EPS)
            && (Math.abs(min - statistic.min) < EPS);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, sum, average, max, min);
    }
}
