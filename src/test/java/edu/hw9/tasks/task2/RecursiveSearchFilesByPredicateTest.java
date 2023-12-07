package edu.hw9.tasks.task2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Function;
import java.util.stream.Stream;
import static edu.testFileCreator.TestFilesCreator.combinePath;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Task 2.1")
class RecursiveSearchFilesByPredicateTest {
    private static final Path HW1 = combinePath(System.getProperty("user.dir"), "src", "main", "java", "edu", "hw1");
    private static final Path HW4 = combinePath(System.getProperty("user.dir"), "src", "main", "java", "edu", "hw4");

    private static Function<Path, Boolean> matchExtension(String extension) {
        PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:*." + extension);
        return x -> matcher.matches(x.getFileName());
    }

    private static Function<Path, Boolean> matchSizeAtLeast(int size) {
        return x -> {
            try {
                return Files.size(x) >= size;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
    }

    public static Stream<Arguments> testRecursiveSearchFilesByPredicate() {
        return Stream.of(
            Arguments.of(
                HW1,
                matchExtension("java"),
                List.of(
                    combinePath(HW1, "Main.java"),
                    combinePath(HW1, "tasks", "Task0.java"),
                    combinePath(HW1, "tasks", "Task1.java"),
                    combinePath(HW1, "tasks", "Task2.java"),
                    combinePath(HW1, "tasks", "Task3.java"),
                    combinePath(HW1, "tasks", "Task4.java"),
                    combinePath(HW1, "tasks", "Task5.java"),
                    combinePath(HW1, "tasks", "Task6.java"),
                    combinePath(HW1, "tasks", "Task7.java"),
                    combinePath(HW1, "tasks", "Task8.java")
                )
            ),
            Arguments.of(
                HW4,
                matchSizeAtLeast(758),
                List.of(
                    combinePath(HW4, "Animal.java"),
                    combinePath(HW4, "tasks", "task17", "Counter.java"),
                    combinePath(HW4, "tasks", "task19", "FindErrorsInAnimals.java"),
                    combinePath(HW4, "tasks", "task20", "FindErrorsInAnimalsVersion2.java")
                )
            )
        );
    }

    @ParameterizedTest
    @MethodSource
    void testRecursiveSearchFilesByPredicate(Path input, Function<Path, Boolean> predicate, List<Path> result) {
        try (ForkJoinPool forkJoinPool = ForkJoinPool.commonPool()) {
            RecursiveSearchFilesByPredicate start = new RecursiveSearchFilesByPredicate(input, predicate);
            List<Path> output = forkJoinPool.invoke(start).stream().sorted().toList();
            assertThat(output).isEqualTo(result);
        }
    }
}
