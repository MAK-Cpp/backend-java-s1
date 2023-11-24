package edu.hw6.tasks.task4;

import edu.testFileCreator.TestFilesCreator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.util.zip.CRC32;
import java.util.zip.Checksum;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Task 4")
class OutputStreamCompositionTest {
    private static final TestFilesCreator CREATOR =
        new TestFilesCreator(Paths.get(System.getProperty("user.dir")).resolve(".StreamComposition"));

    @BeforeAll
    static void setUp() throws IOException {
        CREATOR.createDirectory();
    }

    public static Stream<Arguments> testOutputStreamComposition() {
        return Stream.of(
            Arguments.of(
                Path.of("file-1.txt"),
                new CRC32(),
                "Programming is learned by writing programs. ― Brian Kernighan"
            )
        );
    }

    @ParameterizedTest
    @MethodSource
    void testOutputStreamComposition(final Path file, final Checksum checksum, final String text) throws IOException {
        OutputStreamComposition.compose(CREATOR.root().resolve(file), checksum, text);
        assertThat(Files.readAllBytes(CREATOR.root().resolve(file))).isEqualTo(text.getBytes(StandardCharsets.UTF_8));
    }

    @AfterAll
    static void tearDown() throws IOException {
        CREATOR.deleteDirectory();
    }
}
