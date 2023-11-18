package edu.hw6.tasks.task1;

import edu.hw6.tasks.TestFilesCreator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import static edu.hw6.tasks.TestFilesCreator.combinePath;
import static edu.hw6.tasks.task1.DiskMapOperations.*;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Task 1")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DiskMapTest {
    private static final TestFilesCreator CREATOR =
        new TestFilesCreator(Paths.get(System.getProperty("user.dir")).resolve(".DiskMaps"));

    @BeforeAll
    static void setUp() throws IOException {
        CREATOR.createDirectory();
        CREATOR.newTestFile("created-file-No1.dm", "a=b");
        CREATOR.newTestFile("created-file-No2.dm", "");
        CREATOR.newTestFile(
            combinePath("test folder", "created-file-No3.dm"),
            "Test=file\nHello=World!\nBank=Tinkoff\nUniversity=ITMO"
        );
    }

    private static Map.Entry<DiskMapOperations, List<String>> get(final String key, final String value) {
        return Map.entry(GET, Arrays.asList(key, value));
    }

    private static Map.Entry<DiskMapOperations, List<String>> put(
        final String key,
        final String value,
        final String previous
    ) {
        return Map.entry(PUT, Arrays.asList(key, value, previous));
    }

    private static Map.Entry<DiskMapOperations, List<String>> remove(final String key, final String value) {
        return Map.entry(REMOVE, Arrays.asList(key, value));
    }

    public static Stream<Arguments> testNewDiskMap() {
        return Stream.of(
            Arguments.of(
                "new-file-No0",
                Path.of(""),
                List.of(),
                Map.of()
            ),
            Arguments.of(
                "new-file-No1",
                Path.of(""),
                List.of(get("a", null), remove("b", null)),
                Map.of()
            ),
            Arguments.of(
                "new-file-No2",
                Path.of(""),
                List.of(
                    put("language", "java", null),
                    put("better language", "c++", null),
                    put("best language", "python", null),
                    get("better language", "c++"),
                    put("best language", "scratch", "python"),
                    get("best language", "scratch"),
                    put("trash", "python", null),
                    remove("trash", "python")
                ),
                Map.of("language", "java", "better language", "c++", "best language", "scratch")
            ),
            Arguments.of(
                "new-file-No3",
                Path.of("folder for new files"),
                List.of(
                    put("version", "1.0.0", null),
                    put("branch", "master", null),
                    put("remote repository", "https://github.com/MAK-Cpp/backend-java-s1-hw1.git", null)
                ),
                Map.of(
                    "version", "1.0.0",
                    "branch", "master",
                    "remote repository", "https://github.com/MAK-Cpp/backend-java-s1-hw1.git"
                )
            )
        );
    }

    public static Stream<Arguments> testExistingDiskMap() {
        return Stream.of(
            Arguments.of(
                Path.of("new-file-No0.dm"),
                Map.of(),
                List.of(),
                Map.of()
            ),
            Arguments.of(
                Path.of("new-file-No1.dm"),
                Map.of(),
                List.of(get("a", null), remove("b", null)),
                Map.of()
            ),
            Arguments.of(
                Path.of("new-file-No2.dm"),
                Map.of("language", "java", "better language", "c++", "best language", "scratch"),
                List.of(
                    put("IDE", "vim", null),
                    put("OS", "TempleOS", null),
                    remove("better language", "c++"),
                    remove("best language", "scratch"),
                    put("language", "HolyC", "java")
                ),
                Map.of("IDE", "vim", "OS", "TempleOS", "language", "HolyC")
            ),
            Arguments.of(
                Path.of("folder for new files").resolve("new-file-No3.dm"),
                Map.of(
                    "version", "1.0.0",
                    "branch", "master",
                    "remote repository", "https://github.com/MAK-Cpp/backend-java-s1-hw1.git"
                ),
                List.of(
                    get("remote repository", "https://github.com/MAK-Cpp/backend-java-s1-hw1.git"),
                    put("new branch", "development", null),
                    put("development last commit", "445a8b101d85f4682d50739ef4ca2bad45396076", null),
                    put(
                        "development last commit",
                        "041046ca6b6490f675dd0915e95ec24a2b250d71",
                        "445a8b101d85f4682d50739ef4ca2bad45396076"
                    ),
                    put("version", "1.1.2", "1.0.0"),
                    get("remote repository", "https://github.com/MAK-Cpp/backend-java-s1-hw1.git"),
                    put("pull request №1 from", "development", null),
                    put("pull request №1 to", "master", null)
                ),
                Map.of(
                    "version", "1.1.2",
                    "branch", "master",
                    "remote repository", "https://github.com/MAK-Cpp/backend-java-s1-hw1.git",
                    "new branch", "development",
                    "development last commit", "041046ca6b6490f675dd0915e95ec24a2b250d71",
                    "pull request №1 from", "development",
                    "pull request №1 to", "master"
                )
            ),
            Arguments.of(
                Path.of("created-file-No1.dm"),
                Map.of("a", "b"),
                List.of(
                    get("a", "b"),
                    get("b", null),
                    put("c", "d", null),
                    put("itmo", "ct", null),
                    remove("a", "b"),
                    put("c", "t", "d")
                ),
                Map.of("c", "t", "itmo", "ct")
            ),
            Arguments.of(
                Path.of("created-file-No2.dm"),
                Map.of(),
                List.of(
                    put("now this file", "is not empty", null),
                    get("not existing key", null),
                    remove("not existing key", null)
                ),
                Map.of("now this file", "is not empty")
            ),
            Arguments.of(
                Path.of("test folder").resolve("created-file-No3.dm"),
                Map.of("Test", "file", "Hello", "World!", "Bank", "Tinkoff", "University", "ITMO"),
                List.of(
                    remove("Test", "file"),
                    get("Hello", "World!"),
                    put("Hello", "Привет", "World!"),
                    put("Department", "CT 01.03.02", null),
                    put("School", "Physics and Mathematics Lyceum No. 31", null),
                    put("Hobby", "Guitar", null)
                ),
                Map.of(
                    "Hello", "Привет",
                    "Bank", "Tinkoff",
                    "University", "ITMO",
                    "Department", "CT 01.03.02",
                    "School", "Physics and Mathematics Lyceum No. 31",
                    "Hobby", "Guitar"
                )
            )
        );
    }

    private void testOperations(
        final DiskMap map,
        final List<Map.Entry<DiskMapOperations, List<String>>> operations,
        final Map<String, String> output
    ) {
        for (Map.Entry<DiskMapOperations, List<String>> operation : operations) {
            switch (operation.getKey()) {
                case GET -> assertThat(map.get(operation.getValue().get(0))).isEqualTo(operation.getValue().get(1));
                case PUT -> assertThat(map.put(
                    operation.getValue().get(0),
                    operation.getValue().get(1)
                )).isEqualTo(operation.getValue().get(2));
                case REMOVE ->
                    assertThat(map.remove(operation.getValue().get(0))).isEqualTo(operation.getValue().get(1));
            }
        }
        assertThat(map.entrySet()).isEqualTo(output.entrySet());
        map.save();
    }

    @ParameterizedTest
    @MethodSource
    @Order(1)
    void testNewDiskMap(
        final String filename,
        final Path directory,
        final List<Map.Entry<DiskMapOperations, List<String>>> operations,
        final Map<String, String> output
    ) {
        testOperations(DiskMap.create(CREATOR.root().resolve(directory), filename), operations, output);
    }

    @ParameterizedTest
    @MethodSource
    @Order(2)
    void testExistingDiskMap(
        final Path fileDirectory,
        final Map<String, String> input,
        final List<Map.Entry<DiskMapOperations, List<String>>> operations,
        final Map<String, String> output
    ) {
        final DiskMap map = DiskMap.open(CREATOR.root().resolve(fileDirectory));
        assertThat(map.entrySet()).isEqualTo(input.entrySet());
        testOperations(map, operations, output);
    }

    @AfterAll
    static void tearDown() throws IOException {
        CREATOR.deleteDirectory();
    }
}
