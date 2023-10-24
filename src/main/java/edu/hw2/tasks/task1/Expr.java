package edu.hw2.tasks.task1;

public sealed interface Expr permits Addition, Constant, Exponent, Multiplication, Negate {
    double evaluate();

    String toString();
}
