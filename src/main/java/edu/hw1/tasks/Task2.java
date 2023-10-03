package edu.hw1.tasks;

public final class Task2 {
    static final private int NUMBER_SYSTEM = 10;

    private Task2() {
    }

    public static int countDigits(final int integer) {
        if (integer == 0) {
            return 1;
        }
        int ans = 0;
        int integerToParse = integer;
        if (integer > 0) {
            for (; integerToParse > 0; ans++, integerToParse /= NUMBER_SYSTEM) {
            }
        } else {
            for (; integerToParse < 0; ans++, integerToParse /= NUMBER_SYSTEM) {
            }
        }
        return ans;
    }
}
