package edu.hw2.tasks.task1;

public record Exponent(Expr operand, double exponent) implements Expr {
    @Override
    public double evaluate() {
        return Math.pow(operand.evaluate(), exponent);
    }

    @Override
    public String toString() {
        return '(' + operand.toString() + ")^" + exponent;
    }
}
