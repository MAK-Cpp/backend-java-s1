package edu.hw6.tasks;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import static java.nio.file.StandardOpenOption.CREATE_NEW;
import static java.nio.file.StandardOpenOption.WRITE;

public record TestFilesCreator(Path root) {

    public void createDirectory() throws IOException {
        Files.createDirectories(root);
    }

    public void newTestFile(final Path file, final byte[] bytes) throws IOException {
        final Path path = root.resolve(file);
        if (!Files.exists(path.getParent())) {
            Files.createDirectories(path.getParent());
        }
        FileChannel outChannel = FileChannel.open(path, WRITE, CREATE_NEW);
        outChannel.write(ByteBuffer.wrap(bytes));
        outChannel.close();
    }

    public void newTestFile(final String file, final byte[] bytes) throws IOException {
        newTestFile(Path.of(file), bytes);
    }

    public void newTestFile(final Path file, final String text) throws IOException {
        newTestFile(file, text.getBytes(StandardCharsets.UTF_8));
    }

    public void newTestFile(final String file, final String text) throws IOException {
        newTestFile(Path.of(file), text.getBytes(StandardCharsets.UTF_8));
    }

    public static Path combinePath(final String... paths) {
        Path result = Path.of("");
        for (final String path : paths) {
            result = result.resolve(path);
        }
        return result;
    }

    public void deleteDirectory() throws IOException {
        Files.walkFileTree(root, new DeleteRecursiveFileVisitor());
    }
}
