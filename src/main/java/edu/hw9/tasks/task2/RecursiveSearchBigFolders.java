package edu.hw9.tasks.task2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Stream;

public class RecursiveSearchBigFolders extends RecursiveTask<List<Path>> {
    private final Path currentPath;
    private final long countFilesIntPath;
    private static final int COUNT_FILES = 1000;

    public RecursiveSearchBigFolders(Path currentPath) {
        this.currentPath = currentPath;
        try (Stream<Path> pathStream = Files.list(currentPath)) {
            this.countFilesIntPath = pathStream.count();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected List<Path> compute() {
        if (Files.isRegularFile(currentPath) || (Files.isDirectory(currentPath) && countFilesIntPath <= COUNT_FILES)) {
            return List.of();
        }
        try (Stream<Path> pathStream = Files.list(currentPath)) {
            ArrayList<Path> result = new ArrayList<>();
            result.add(currentPath);
            return ForkJoinTask.invokeAll(pathStream.map(RecursiveSearchBigFolders::new).toList()).stream()
                .map(ForkJoinTask::join).reduce(result, (x, y) -> {
                    x.addAll(y);
                    return x;
                });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
