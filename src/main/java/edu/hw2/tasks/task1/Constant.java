package edu.hw2.tasks.task1;

public record Constant(double value) implements Expr {
    @Override
    public double evaluate() {
        return value;
    }

    @Override
    public String toString() {
        return Double.toString(value);
    }
}
