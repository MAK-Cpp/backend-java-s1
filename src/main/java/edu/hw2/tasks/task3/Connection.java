package edu.hw2.tasks.task3;

public interface Connection extends AutoCloseable {
    void execute(String command);
}
