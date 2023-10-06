package edu.hw1.tasks;

public final class Task5 {
    private static final int NUMBER_SYSTEM = 10;

    private Task5() {
    }

    private static int reverseNumber(final int number) {
        int reversedNumber = 0;
        int originalNumber = number;
        while (originalNumber > 0) {
            reversedNumber = reversedNumber * NUMBER_SYSTEM + (originalNumber % NUMBER_SYSTEM);
            originalNumber /= NUMBER_SYSTEM;
        }
        return reversedNumber;
    }

    private static boolean isPalindrome(final int number) {
        return number == reverseNumber(number);
    }

    private static int getDescendant(final int number) {
        int descendant = 0;
        int reversedNumber = reverseNumber(number);
        while (reversedNumber >= NUMBER_SYSTEM) {
            int firstNum = reversedNumber % NUMBER_SYSTEM;
            reversedNumber /= NUMBER_SYSTEM;
            int secondNum = reversedNumber % NUMBER_SYSTEM;
            reversedNumber /= NUMBER_SYSTEM;
            descendant =
                descendant * (firstNum + secondNum < NUMBER_SYSTEM ? NUMBER_SYSTEM : NUMBER_SYSTEM * NUMBER_SYSTEM)
                    + firstNum + secondNum;
        }
        if (reversedNumber > 0) {
            descendant = descendant * NUMBER_SYSTEM + reversedNumber;
        }
        return descendant;
    }

    public static boolean isPalindromeDescendant(final int number) {
        int anotherNumber;
        for (anotherNumber = number;
             anotherNumber >= NUMBER_SYSTEM && !isPalindrome(anotherNumber);
             anotherNumber = getDescendant(anotherNumber)) {
        }
        return anotherNumber >= NUMBER_SYSTEM;
    }
}
