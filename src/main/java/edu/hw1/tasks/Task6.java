package edu.hw1.tasks;

public class Task6 {
    private Task6() {
    }

    private static int getDelta(int number) {
        int a = 0;
        int b = 0;
        int c = 0;
        int d = 0;
        while (number > 0) {
            int anotherNumber = number % 10;
            number /= 10;
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
        return (b - c) * 90 + (a - d) * 999;
    }

    private static int countK(int number, int step) {
        return (number == 6174 ? step : countK(getDelta(number), step + 1));
    }

    public static int countK(int number) {
        return countK(number, 0);
    }
}
