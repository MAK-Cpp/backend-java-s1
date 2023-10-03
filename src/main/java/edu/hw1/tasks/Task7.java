package edu.hw1.tasks;

public final class Task7 {
    private Task7() {
    }

    private static int numberBinaryLength(final int number) {
        return (int) (Math.log(number) / Math.log(2)) + 1;
    }

    public static int rotateRight(final int numberToRotate, final int shift) {
        final int NUMBER_BINARY_LENGTH = numberBinaryLength(numberToRotate);
        int refactoredShift =
            (shift >= 0
                ? shift % NUMBER_BINARY_LENGTH
                : NUMBER_BINARY_LENGTH - (Math.abs(shift) % NUMBER_BINARY_LENGTH));
        int ans = numberToRotate;
        final int MAX_BIT = (1 << (NUMBER_BINARY_LENGTH - 1));
        for (; refactoredShift > 0; refactoredShift--) {
            ans = (ans >> 1) + MAX_BIT * ((ans & 1) == 1 ? 1 : 0);
        }

        return ans;
    }

    public static int rotateLeft(final int numberToRotate, final int shift) {
        return rotateRight(numberToRotate, numberBinaryLength(numberToRotate) - shift);
    }
}
