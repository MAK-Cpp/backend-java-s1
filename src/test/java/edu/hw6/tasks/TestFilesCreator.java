package edu.hw6.tasks;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import static java.nio.file.StandardOpenOption.CREATE_NEW;
import static java.nio.file.StandardOpenOption.WRITE;

public record TestFilesCreator(Path root) {

    public void createDirectory() throws IOException {
        Files.createDirectories(root);
    }

    public void newTestFile(final Path file, final String text) throws IOException {
        final Path path = root.resolve(file);
        if (!Files.exists(path.getParent())) {
            Files.createDirectories(path.getParent());
        }
        FileChannel outChannel = FileChannel.open(path, WRITE, CREATE_NEW);
        outChannel.write(ByteBuffer.wrap(text.getBytes(StandardCharsets.UTF_8)));
        outChannel.close();
    }

    public void newTestFile(final String file, final String text) throws IOException {
        newTestFile(Path.of(file), text);
    }

    public void deleteDirectory() throws IOException {
        Files.walkFileTree(root, new DeleteRecursiveFileVisitor());
    }
}
