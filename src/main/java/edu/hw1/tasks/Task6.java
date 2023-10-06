package edu.hw1.tasks;

public final class Task6 {
    private static final int NUMBER_SYSTEM = 10;
    private static final int CAPREKARS_CONST = 6174;

    private Task6() {
    }

    private static int getDelta(final int number) {
        int a = 0;
        int b = 0;
        int c = 0;
        int d = 0;
        int toParse = number;
        while (toParse > 0) {
            int anotherNumber = toParse % NUMBER_SYSTEM;
            toParse /= NUMBER_SYSTEM;
            if (a < anotherNumber) {
                d = c;
                c = b;
                b = a;
                a = anotherNumber;
            } else if (b < anotherNumber) {
                d = c;
                c = b;
                b = anotherNumber;
            } else if (c < anotherNumber) {
                d = c;
                c = anotherNumber;
            } else if (d < anotherNumber) {
                d = anotherNumber;
            }
        }
        final int FIRST_CONSTANT = 90;
        final int SECOND_CONSTANT = 999;
        return (b - c) * FIRST_CONSTANT + (a - d) * SECOND_CONSTANT;
    }

    private static int countK(final int number, final int step) {
        return (number == CAPREKARS_CONST ? step : countK(getDelta(number), step + 1));
    }

    public static int countK(final int number) {
        return countK(number, 0);
    }
}
