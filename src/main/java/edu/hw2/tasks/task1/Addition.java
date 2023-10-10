package edu.hw2.tasks.task1;

public record Addition(Expr operandA, Expr operandB) implements Expr {
    @Override
    public double evaluate() {
        return operandA.evaluate() + operandB.evaluate();
    }

    @Override
    public String toString() {
        return '(' + operandA.toString() + " + " + operandB.toString() + ')';
    }
}
