package edu.hw9.tasks.task2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;

public class RecursiveSearchFilesByPredicate extends RecursiveSearchFileSystem {
    private final Function<Path, Boolean> predicate;

    public RecursiveSearchFilesByPredicate(Path currentPath, Function<Path, Boolean> predicate) {
        super(currentPath);
        this.predicate = predicate;
    }

    @Override
    public RecursiveSearchFilesByPredicate subtask(Path path) {
        return new RecursiveSearchFilesByPredicate(path, predicate);
    }

    @Override
    protected List<Path> compute() {
        if (Files.isRegularFile(currentPath)) {
            if (predicate.apply(currentPath)) {
                return List.of(currentPath);
            }
            return List.of();
        }
        return calculate();
    }
}
