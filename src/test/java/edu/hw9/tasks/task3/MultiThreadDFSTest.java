package edu.hw9.tasks.task3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Task 3")
class MultiThreadDFSTest {
    public static Stream<Arguments> testMultiThreadDFS() {
        return Stream.of(
            Arguments.of(
                List.of(
                    List.of(17, 18),
                    List.of(17, 18),
                    List.of(20, 23),
                    List.of(26),
                    List.of(5, 6, 33),
                    List.of(4),
                    List.of(4),
                    List.of(27),
                    List.of(22, 28),
                    List.of(20, 32),
                    List.of(20, 22),
                    List.of(24),
                    List.of(13, 25),
                    List.of(12, 14),
                    List.of(13),
                    List.of(24),
                    List.of(23),
                    List.of(0, 1),
                    List.of(0, 1),
                    List.of(20),
                    List.of(2, 9, 10, 19, 21),
                    List.of(20),
                    List.of(8, 10),
                    List.of(2, 16, 24),
                    List.of(11, 15, 23),
                    List.of(12, 26, 33),
                    List.of(3, 25, 33),
                    List.of(7, 28, 34),
                    List.of(8, 27, 29),
                    List.of(28, 30),
                    List.of(29, 32, 35),
                    List.of(32),
                    List.of(9, 30, 31),
                    List.of(4, 25, 26),
                    List.of(27),
                    List.of(30)
                ),
                List.of(1, 1, 2, 3, 3, 3, 3, 2, 2, 2, 2, 2, 3, 3, 3, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 3, 3, 2, 2, 2, 2, 2, 2, 3, 2, 2)
            )
        );
    }

    @ParameterizedTest
    @MethodSource
    void testMultiThreadDFS(List<List<Integer>> input, List<Integer> result) {
        MultiThreadDFS dfs = new MultiThreadDFS(input);
        assertThat(dfs.findComponents()).isEqualTo(result);
    }
}
