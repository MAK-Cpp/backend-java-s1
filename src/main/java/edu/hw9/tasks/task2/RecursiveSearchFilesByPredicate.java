package edu.hw9.tasks.task2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.function.Function;
import java.util.stream.Stream;

public class RecursiveSearchFilesByPredicate extends RecursiveTask<List<Path>> {
    private final Path currentPath;
    private final Function<Path, Boolean> predicate;

    public RecursiveSearchFilesByPredicate(Path currentPath, Function<Path, Boolean> predicate) {
        this.currentPath = currentPath;
        this.predicate = predicate;
    }

    @Override
    protected List<Path> compute() {
        if (Files.isRegularFile(currentPath)) {
            if (predicate.apply(currentPath)) {
                return List.of(currentPath);
            }
            return List.of();
        }
        try (Stream<Path> pathStream = Files.list(currentPath)) {
            return ForkJoinTask.invokeAll(pathStream.map(x -> new RecursiveSearchFilesByPredicate(x, predicate))
                .toList()).stream().map(ForkJoinTask::join).reduce(new ArrayList<>(), (x, y) -> {
                x.addAll(y);
                return x;
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
