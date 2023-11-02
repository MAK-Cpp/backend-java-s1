package edu.hw3.tasks.task8;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;

public class BackwardIteratorTest {
    @ParameterizedTest
    @MethodSource("collections")
    public <T> void testBackwardIterator(final Collection<T> collection, final T[] result) {
        Iterator<T> iterator = new BackwardIterator<>(collection);
        int index = 0;
        while (iterator.hasNext()) {
            assertThat(iterator.next()).isEqualTo(result[index++]);
        }
    }

    public static Stream<Arguments> collections() {
        return Stream.of(
            Arguments.of(
                List.of(1, 2, 3),
                new Integer[] {3, 2, 1}
            ),
            Arguments.of(
                Arrays.asList("a", "b", "C"),
                new String[] {"C", "b", "a"}
            ),
            Arguments.of(
                new LinkedList<>(List.of(true, false, false)),
                new Boolean[] {false, false, true}
            )
        );
    }
}
