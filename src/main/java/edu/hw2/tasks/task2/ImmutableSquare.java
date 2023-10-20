package edu.hw2.tasks.task2;

public class ImmutableSquare extends ImmutableRectangle {
    ImmutableSquare() {
        super(0, 0);
    }

    ImmutableSquare(double size) {
        super(size, size);
    }
}
