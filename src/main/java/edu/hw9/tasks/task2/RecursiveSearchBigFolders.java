package edu.hw9.tasks.task2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class RecursiveSearchBigFolders extends RecursiveSearchFileSystem {
    private final long countFilesIntPath;
    private static final int COUNT_FILES = 1_000;

    public RecursiveSearchBigFolders(Path currentPath) {
        super(currentPath);
        try (Stream<Path> pathStream = Files.list(currentPath)) {
            this.countFilesIntPath = pathStream.count();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    RecursiveSearchFileSystem subtask(Path subpath) {
        return new RecursiveSearchBigFolders(subpath);
    }

    @Override
    protected List<Path> compute() {
        if (Files.isRegularFile(currentPath) || (Files.isDirectory(currentPath) && countFilesIntPath <= COUNT_FILES)) {
            return List.of();
        }
        ArrayList<Path> result = new ArrayList<>();
        result.add(currentPath);
        return calculate(result);
    }
}
