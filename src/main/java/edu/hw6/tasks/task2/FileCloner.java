package edu.hw6.tasks.task2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class FileCloner {
    private FileCloner() {
    }

    public static String[] splitFilename(final String filename) {
        return filename.split("\\.(?=[^\\.]+$)");
    }

    private static Path clone(final Path from, final Path to) {
        try {
            Files.copy(from, to);
            return to;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Path cloneFile(final Path path) {
        if (!Files.exists(path)) {
            throw new IllegalArgumentException("path " + path + " does not exist");
        } else if (!Files.isRegularFile(path)) {
            throw new IllegalArgumentException(path + " is not a file");
        }
        final Path directory = path.getParent();
        final String[] filenameAndExtension = splitFilename(path.getFileName().toString());
        String extension = "";
        if (filenameAndExtension.length != 1) {
            extension = "." + filenameAndExtension[1];
        }
        String clonedFilename = filenameAndExtension[0] + " — копия";
        if (!Files.exists(directory.resolve(clonedFilename + extension))) {
            return clone(path, directory.resolve(clonedFilename + extension));
        }
        int i;
        for (i = 2;
             Files.exists(directory.resolve(clonedFilename + " (" + i + ")" + extension));
             i++) {
        }
        return clone(path, directory.resolve(clonedFilename + " (" + i + ")" + extension));
    }
}
