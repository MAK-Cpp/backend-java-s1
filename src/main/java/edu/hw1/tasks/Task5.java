package edu.hw1.tasks;

public class Task5 {
    private Task5() {
    }

    private static int reverseNumber(int number) {
        int reversedNumber = 0;
        while (number > 0) {
            reversedNumber = reversedNumber * 10 + (number % 10);
            number /= 10;
        }
        return reversedNumber;
    }

    private static boolean isPalindrome(final int number) {
        return number == reverseNumber(number);
    }

    private static int getDescendant(int number) {
        int descendant = 0;
        number = reverseNumber(number);
        while (number >= 10) {
            int firstNum = number % 10;
            number /= 10;
            int secondNum = number % 10;
            number /= 10;
            descendant = descendant * (firstNum + secondNum < 10 ? 10 : 100) + firstNum + secondNum;
        }
        if (number > 0) {
            descendant = descendant * 10 + number;
        }
        return descendant;
    }

    public static boolean isPalindromeDescendant(int number) {
        while (number >= 10 && !isPalindrome(number)) {
            number = getDescendant(number);
        }
        return number >= 10 && isPalindrome(number);
    }
}
