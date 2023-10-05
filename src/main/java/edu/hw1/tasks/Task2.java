package edu.hw1.tasks;

public final class Task2 {
    private static final int NUMBER_SYSTEM = 10;

    private Task2() {
    }

    public static int countDigits(final int integer) {
        if (integer == 0) {
            return 1;
        }
        int ans = 0;
        if (integer > 0) {
            for (int integerToParse = integer; integerToParse > 0; ans++, integerToParse /= NUMBER_SYSTEM) {
            }
        } else {
            for (int integerToParse = integer; integerToParse < 0; ans++, integerToParse /= NUMBER_SYSTEM) {
            }
        }
        return ans;
    }
}
