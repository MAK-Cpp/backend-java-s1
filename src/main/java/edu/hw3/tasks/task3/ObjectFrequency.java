package edu.hw3.tasks.task3;

import java.util.HashMap;

public final class ObjectFrequency {
    private ObjectFrequency() {
    }

    public static <T> HashMap<T, Integer> freqDict(final T[] dictionary) {
        HashMap<T, Integer> frequency = new HashMap<>();
        for (T element : dictionary) {
            frequency.put(element, frequency.getOrDefault(element, 0) + 1);
        }
        return frequency;
    }
}
