package edu.hw7.tasks.task2;

import java.math.BigInteger;
import java.util.stream.Stream;

public final class ThreadFactorial {
    private static final BigInteger ONE = BigInteger.valueOf(1);

    private ThreadFactorial() {
    }

    public static BigInteger calculate(int n) {
        return Stream.iterate(ONE, i -> i.add(ONE)).limit(n).parallel().reduce(ONE, BigInteger::multiply);
    }
}
