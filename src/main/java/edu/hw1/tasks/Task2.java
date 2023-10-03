package edu.hw1.tasks;

public class Task2 {
    private Task2() {
    }

    public static int countDigits(int integer) {
        if (integer == 0) {
            return 1;
        }
        int ans = 0;
        if (integer > 0) {
            for (; integer > 0; ans++, integer /= 10) {
            }
        } else {
            for (; integer < 0; ans++, integer /= 10) {
            }
        }
        return ans;
    }
}
