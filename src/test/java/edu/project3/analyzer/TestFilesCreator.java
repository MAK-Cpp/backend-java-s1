package edu.project3.analyzer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
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

    public void newTestFile(final Path file, final int[] bigBytes) throws IOException {
        final List<Byte> bytes = Arrays.stream(bigBytes).mapToObj(x -> (byte) x).toList();
        final byte[] rawBytes = new byte[bytes.size()];
        for (int i = 0; i < rawBytes.length; i++) {
            rawBytes[i] = bytes.get(i);
        }
        newTestFile(file, rawBytes);
    }

    public void newTestFile(final String file, final int[] bigBytes) throws IOException {
        newTestFile(Path.of(file), bigBytes);
    }

    public static Path combinePath(final String beginPath, final String... paths) {
        Path result = Path.of(beginPath);
        for (final String path : paths) {
            result = result.resolve(path);
        }
        return result;
    }

    public void deleteDirectory() throws IOException {
        Files.walkFileTree(root, new DeleteRecursiveFileVisitor());
    }
}
