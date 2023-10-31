package edu.hw3.tasks.task3;

import java.util.HashMap;

public final class ObjectFrequency {
    private ObjectFrequency() {
    }

    public static <T> HashMap<T, Integer> freqDict(final T[] dictionary) {
        HashMap<T, Integer> frequency = new HashMap<>();
        for (T element : dictionary) {
            frequency.merge(element, 1, Integer::sum);
        }
        return frequency;
    }
}
