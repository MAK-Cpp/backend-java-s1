package edu.hw2.tasks.task2;

public class ImmutableSquare extends ImmutableRectangle {
    ImmutableSquare() {
        super(0, 0);
    }

    ImmutableSquare(int size) {
        super(size, size);
    }
}
