package edu.hw4.tasks;

import java.util.Collections;
import java.util.List;

public final class ShuffleList {
    private ShuffleList() {
    }

    public static <T> List<T> shuffle(final List<T> list) {
        List<T> answer = new java.util.ArrayList<>(list);
        Collections.shuffle(answer);
        return answer;
    }
}
