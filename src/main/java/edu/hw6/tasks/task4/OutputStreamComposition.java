package edu.hw6.tasks.task4;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.CheckedOutputStream;
import java.util.zip.Checksum;

public final class OutputStreamComposition {
    private OutputStreamComposition() {
    }

    public static void compose(final Path file, final Checksum checksum, final String text) throws IOException {
        Files.createDirectories(file.getParent());
        Files.createFile(file);
        try (OutputStream stream = new FileOutputStream(file.toFile());
             CheckedOutputStream checkedOutputStream = new CheckedOutputStream(stream, checksum);
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(checkedOutputStream);
             OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                 bufferedOutputStream,
                 StandardCharsets.UTF_8
             );
             PrintWriter printWriter = new PrintWriter(outputStreamWriter)) {
            printWriter.print(text);
        }
    }
}
