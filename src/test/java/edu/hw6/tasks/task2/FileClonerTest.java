package edu.hw6.tasks.task2;

import edu.hw6.tasks.TestFilesCreator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;

class FileClonerTest {
    private static final TestFilesCreator CREATOR =
        new TestFilesCreator(Paths.get(System.getProperty("user.dir")).resolve(".ClonedFiles"));

    @BeforeAll
    static void setUp() throws IOException {
        CREATOR.createDirectory();
        CREATOR.newTestFile(
            "Tinkoff Bank Biggest Secret.txt",
            "Tinkoff Bank Biggest Secret is.....\n\n\n IS......\n\n\n\n\n\n IIIIISSSSSS...............\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n mayonnaise"
        );
        CREATOR.newTestFile(
            ".I.VERY.LIKE.DOTS.IT.IS.MY.LOVELY.PART.OF.FILENAME...........WOW.dot",
            "i have a lot of dots\nlike this: .\nor this: ·\nor even this: ˙\n\ni have symbols with dots too\nfor example: ż, Ͼ, Ṙ\n\nDo you wanna 4 dots by time? Me too!: ᠅"
        );
        CREATOR.newTestFile(
            Path.of("hidden folder asf").resolve("another hidden folder omg"),
            "the most hidden file in history.hd",
            "im hiding here\nnobody will find me there\nhehe"
        );
        CREATOR.newTestFile(Path.of("hidden folder asf"), "file without extension", "just a file without extension");
    }

    public static Stream<Arguments> testFileCloner() {
        return Stream.of(
            Arguments.of(
                CREATOR.root().resolve("Tinkoff Bank Biggest Secret.txt"),
                "Tinkoff Bank Biggest Secret — копия.txt"
            ),
            Arguments.of(
                CREATOR.root().resolve("Tinkoff Bank Biggest Secret.txt"),
                "Tinkoff Bank Biggest Secret — копия (2).txt"
            ),
            Arguments.of(
                CREATOR.root().resolve("Tinkoff Bank Biggest Secret.txt"),
                "Tinkoff Bank Biggest Secret — копия (3).txt"
            ),
            Arguments.of(
                CREATOR.root().resolve(".I.VERY.LIKE.DOTS.IT.IS.MY.LOVELY.PART.OF.FILENAME...........WOW.dot"),
                ".I.VERY.LIKE.DOTS.IT.IS.MY.LOVELY.PART.OF.FILENAME...........WOW — копия.dot"
            ),
            Arguments.of(
                CREATOR.root().resolve(".I.VERY.LIKE.DOTS.IT.IS.MY.LOVELY.PART.OF.FILENAME...........WOW.dot"),
                ".I.VERY.LIKE.DOTS.IT.IS.MY.LOVELY.PART.OF.FILENAME...........WOW — копия (2).dot"
            ),
            Arguments.of(
                CREATOR.root().resolve(".I.VERY.LIKE.DOTS.IT.IS.MY.LOVELY.PART.OF.FILENAME...........WOW.dot"),
                ".I.VERY.LIKE.DOTS.IT.IS.MY.LOVELY.PART.OF.FILENAME...........WOW — копия (3).dot"
            ),
            Arguments.of(
                CREATOR.root().resolve(".I.VERY.LIKE.DOTS.IT.IS.MY.LOVELY.PART.OF.FILENAME...........WOW.dot"),
                ".I.VERY.LIKE.DOTS.IT.IS.MY.LOVELY.PART.OF.FILENAME...........WOW — копия (4).dot"
            ),
            Arguments.of(
                CREATOR.root().resolve(".I.VERY.LIKE.DOTS.IT.IS.MY.LOVELY.PART.OF.FILENAME...........WOW — копия.dot"),
                ".I.VERY.LIKE.DOTS.IT.IS.MY.LOVELY.PART.OF.FILENAME...........WOW — копия — копия.dot"
            ),
            Arguments.of(
                CREATOR.root().resolve("Tinkoff Bank Biggest Secret — копия (3).txt"),
                "Tinkoff Bank Biggest Secret — копия (3) — копия.txt"
            ),
            Arguments.of(
                CREATOR.root().resolve("Tinkoff Bank Biggest Secret — копия (3).txt"),
                "Tinkoff Bank Biggest Secret — копия (3) — копия (2).txt"
            ),
            Arguments.of(
                CREATOR.root().resolve("hidden folder asf").resolve("another hidden folder omg")
                    .resolve("the most hidden file in history.hd"),
                "the most hidden file in history — копия.hd"
            ),
            Arguments.of(
                CREATOR.root().resolve("hidden folder asf").resolve("another hidden folder omg")
                    .resolve("the most hidden file in history — копия.hd"),
                "the most hidden file in history — копия — копия.hd"
            ),
            Arguments.of(
                CREATOR.root().resolve("hidden folder asf").resolve("another hidden folder omg")
                    .resolve("the most hidden file in history — копия — копия.hd"),
                "the most hidden file in history — копия — копия — копия.hd"
            ),
            Arguments.of(
                CREATOR.root().resolve("hidden folder asf").resolve("file without extension"),
                "file without extension — копия"
            )
        );
    }

    @ParameterizedTest
    @MethodSource
    void testFileCloner(final Path input, final String outputName) throws IOException {
        final Path output = FileCloner.cloneFile(input);
        assertThat(output.getFileName().toString()).isEqualTo(outputName);
        assertThat(output.getParent()).isEqualTo(input.getParent());
        assertThat(Files.mismatch(input, output)).isEqualTo(-1L);
    }

    @AfterAll
    static void tearDown() throws IOException {
        CREATOR.deleteDirectory();
    }
}
