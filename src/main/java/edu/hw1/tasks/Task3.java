package edu.hw1.tasks;

public final class Task3 {
    private Task3() {
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
