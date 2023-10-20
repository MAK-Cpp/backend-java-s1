package edu.hw2.tasks.task2;

public class ImmutableRectangle {
    protected final double width;
    protected final double height;

    ImmutableRectangle() {
        this.width = 0;
        this.height = 0;
    }

    ImmutableRectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    final ImmutableRectangle setWidth(double width) {
        return new ImmutableRectangle(width, height);
    }

    final ImmutableRectangle setHeight(double height) {
        return new ImmutableRectangle(width, height);
    }

    final double area() {
        return width * height;
    }
}
