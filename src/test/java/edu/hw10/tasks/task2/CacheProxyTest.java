package edu.hw10.tasks.task2;

import edu.hw10.tasks.task2.fibCalculator.BaseFibCalculator;
import edu.hw10.tasks.task2.fibCalculator.FibCalculator;
import edu.prettyTable.line.DoubleLine;
import edu.prettyTable.table.ColumnTypedTable;
import edu.prettyTable.table.Table;
import edu.testFileCreator.TestFilesCreator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Task 2")
class CacheProxyTest {
    private static final TestFilesCreator CREATOR =
        new TestFilesCreator(Path.of(System.getProperty("user.dir")).resolve(".cached"));

    public static Stream<Arguments> testWithFibCalculator() {
        return Stream.of(
            Arguments.of(
                new BaseFibCalculator(),
                List.of(
                    Map.entry(5, 5L),
                    Map.entry(10, 55L),
                    Map.entry(15, 610L),
                    Map.entry(20, 6765L),
                    Map.entry(25, 75025L),
                    Map.entry(30, 832040L),
                    Map.entry(35, 9227465L),
                    Map.entry(40, 102334155L),
                    Map.entry(45, 1134903170L),
                    Map.entry(50, 12586269025L)
                )
            )
        );
    }

    @ParameterizedTest
    @MethodSource
    void testWithFibCalculator(FibCalculator calculator, List<Map.Entry<Integer, Long>> requests) {
        FibCalculator proxy = CacheProxy.create(calculator, calculator.getClass());
        for (Map.Entry<Integer, Long> request : requests) {
            assertThat(proxy.fib(request.getKey())).isEqualTo(request.getValue());
        }
    }

    @AfterAll
    static void deleteCacheDirectory() throws IOException {
        CREATOR.deleteDirectory();
    }
}
