package edu.hw10.tasks.task2.fibCalculator;

public class BaseFibCalculator implements FibCalculator {
    public BaseFibCalculator() {
    }

    @Override
    public long fib(int number) {
        if (number <= 2) {
            return 1;
        }
        return fib(number - 1) + fib(number - 2);
    }
}
