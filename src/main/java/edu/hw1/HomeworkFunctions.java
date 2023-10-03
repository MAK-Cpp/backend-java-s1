package edu.hw1;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class HomeworkFunctions {
    private final static Logger LOGGER = LogManager.getLogger();

    private HomeworkFunctions() {
    }

    public static void printHelloWorld() {
        LOGGER.info("Привет, мир!");
    }

    public static int minutesToSeconds(final String time) {
        if (time.isEmpty() || time.charAt(0) == ':' || time.charAt(time.length() - 1) == ':') {
            return -1;
        }
        int minutes = 0;
        int seconds = 0;
        boolean isSeconds = false;
        for (char character : time.toCharArray()) {
            if (character == ':') {
                if (isSeconds) {
                    return -1;
                }
                isSeconds = true;
            } else if ('0' <= character && character <= '9') {
                if (isSeconds) {
                    seconds = seconds * 10 + (character - '0');
                    if (seconds >= 60) {
                        return -1;
                    }
                } else {
                    minutes = minutes * 10 + (character - '0');
                }
            } else {
                return -1;
            }
        }
        if (!isSeconds) {
            return -1;
        }
        return minutes * 60 + seconds;
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

    public static boolean isNestable(int[] arrayA, int[] arrayB) {
        if (arrayA.length == 0 || arrayB.length == 0) {
            return false;
        }
        int maxA = arrayA[0];
        int minA = arrayA[0];
        int maxB = arrayB[0];
        int minB = arrayB[0];
        for (int elementA : arrayA) {
            maxA = Math.max(maxA, elementA);
            minA = Math.min(minA, elementA);
        }
        for (int elementB : arrayB) {
            maxB = Math.max(maxB, elementB);
            minB = Math.min(minB, elementB);
        }
        return minA > minB && maxA < maxB;
    }
}
