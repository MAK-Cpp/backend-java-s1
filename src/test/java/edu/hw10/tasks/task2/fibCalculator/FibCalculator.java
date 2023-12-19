package edu.hw10.tasks.task2.fibCalculator;

import edu.hw10.tasks.task2.Cache;

public interface FibCalculator {
    @Cache(persist = true)
    long fib(int number);
}
