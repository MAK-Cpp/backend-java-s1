package edu.hw2.tasks.task1;

public record Negate(Expr operand) implements Expr {
    @Override
    public double evaluate() {
        return -operand.evaluate();
    }

    @Override
    public String toString() {
        return "-(" + operand.toString() + ')';
    }
}
