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
                    List.of(1), List.of(2, 5), List.of(3, 4), List.of(2), List.of(0, 1),
                    List.of(6, 7), List.of(), List.of(5, 6), List.of(9, 10), List.of(), List.of()
                ),
                0, 5,
                List.of(0, 1, 5)
            ),
            Arguments.of(
                List.of(
                    List.of(1), List.of(2, 5), List.of(3, 4), List.of(2), List.of(0, 1),
                    List.of(6, 7), List.of(), List.of(5, 6), List.of(9, 10), List.of(), List.of()
                ),
                5, 0,
                null
            ),
            Arguments.of(
                List.of(
                    List.of(1), List.of(2, 5), List.of(3, 4), List.of(2), List.of(0, 1),
                    List.of(6, 7), List.of(), List.of(5, 6), List.of(9, 10), List.of(), List.of()
                ),
                3, 6,
                List.of(3, 2, 4, 1, 5, 6)
            )
        );
    }

    @ParameterizedTest
    @MethodSource
    void testMultiThreadDFS(List<List<Integer>> graph, int start, int finish, List<Integer> way) {
        assertThat(MultiThreadDFS.findWay(graph, start, finish)).isEqualTo(way);
    }
}
