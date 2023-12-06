package edu.hw8.tasks.task2;

public interface ThreadPool extends AutoCloseable {
    void start();

    void execute(Runnable runnable);
}
