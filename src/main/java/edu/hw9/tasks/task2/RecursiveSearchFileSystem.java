package edu.hw9.tasks.task2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Stream;

public abstract class RecursiveSearchFileSystem extends RecursiveTask<List<Path>> {
    protected final Path currentPath;

    protected RecursiveSearchFileSystem(Path currentPath) {
        this.currentPath = currentPath;
    }

    abstract RecursiveSearchFileSystem subtask(Path subpath);

    protected List<Path> accumulator(List<Path> x, List<Path> y) {
        x.addAll(y);
        return x;
    }

    protected List<Path> calculate(ArrayList<Path> identity) {
        try (Stream<Path> pathStream = Files.list(currentPath)) {
            var invokedResults = ForkJoinTask.invokeAll(pathStream.map(this::subtask).toList());
            var results = invokedResults.stream().map(ForkJoinTask::join);
            return results.reduce(identity, this::accumulator);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected List<Path> calculate() {
        return calculate(new ArrayList<>());
    }
}
