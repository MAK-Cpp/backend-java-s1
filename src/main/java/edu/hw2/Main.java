package edu.hw2;

import edu.hw2.tasks.task1.Addition;
import edu.hw2.tasks.task1.Constant;
import edu.hw2.tasks.task1.Exponent;
import edu.hw2.tasks.task1.Multiplication;
import edu.hw2.tasks.task1.Negate;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        var two = new Constant(2);
        var four = new Constant(4);
        var negOne = new Negate(new Constant(1));
        var sumTwoFour = new Addition(two, four);
        var mult = new Multiplication(sumTwoFour, negOne);
        var exp = new Exponent(mult, 2);
        var res = new Addition(exp, new Constant(1));

        System.out.println(res + " = " + (res.evaluate()));
    }
}
