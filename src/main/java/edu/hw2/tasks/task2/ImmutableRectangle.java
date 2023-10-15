package edu.hw2.tasks.task2;

public class ImmutableRectangle {
    protected int width;
    protected int height;

    ImmutableRectangle() {
        this.width = 0;
        this.height = 0;
    }

    ImmutableRectangle(int width, int height) {
        this.width = width;
        this.height = height;
    }

    final ImmutableRectangle setWidth(int width) {
        return new ImmutableRectangle(width, height);
    }

    final ImmutableRectangle setHeight(int height) {
        return new ImmutableRectangle(width, height);
    }

    final double area() {
        return width * height;
    }
}
