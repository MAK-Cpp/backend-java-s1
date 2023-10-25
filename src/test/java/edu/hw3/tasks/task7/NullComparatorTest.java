package edu.hw3.tasks.task7;

import edu.hw3.tasks.task6.Stock;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;
import static edu.hw3.tasks.task7.TreeMapOperations.*;

public class NullComparatorTest {
    @ParameterizedTest
    @MethodSource("keysOrNullWithValues")
    public <T, V> void testNullComparator(
        final List<TreeMapOperation<T, V>> keysAndValues,
        final Comparator<T> comparator,
        final List<SimpleEntry<T, V>> resultList
    ) {
        final NullComparator<T> nullComparator = new NullComparator<>(comparator);
        TreeMap<T, V> treeMap = new TreeMap<>(nullComparator);
        final TreeMap<T, V> resultTree = generateAns(resultList, nullComparator);

        for (TreeMapOperation<T, V> entry : keysAndValues) {
            switch (entry.operation()) {
                case CONTAINS_KEY -> assertThat(treeMap).containsKey(entry.key());
                case CONTAINS_VALUE -> assertThat(treeMap).containsValue(entry.value());
                case GET -> assertThat(treeMap.get(entry.key())).isEqualTo(entry.value());
                case PUT -> treeMap.put(entry.key(), entry.value());
                case REMOVE -> assertThat(treeMap.remove(entry.key())).isEqualTo(entry.value());
            }
        }

        assertThat(treeMap).isEqualTo(resultTree);
    }

    private static <T, V> TreeMap<T, V> generateAns(
        final List<SimpleEntry<T, V>> keysAndValues,
        final Comparator<T> comparator
    ) {
        TreeMap<T, V> result = new TreeMap<>(new NullComparator<T>(comparator));
        for (SimpleEntry<T, V> entry : keysAndValues) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    private static <T, V> TreeMapOperation<T, V> get(final T key, final V value) {
        return new TreeMapOperation<>(GET, key, value);
    }

    private static <T, V> TreeMapOperation<T, V> put(final T key, final V value) {
        return new TreeMapOperation<>(PUT, key, value);
    }

    private static <T, V> TreeMapOperation<T, V> remove(final T key, final V value) {
        return new TreeMapOperation<>(REMOVE, key, value);
    }

    private static <T, V> TreeMapOperation<T, V> containsKey(final T key) {
        return new TreeMapOperation<>(CONTAINS_KEY, key, null);
    }

    private static <T, V> TreeMapOperation<T, V> containsValue(final V value) {
        return new TreeMapOperation<>(CONTAINS_VALUE, null, value);
    }

    private static Stream<Arguments> keysOrNullWithValues() {
        return Stream.of(
            Arguments.of(
                Arrays.asList(
                    put("abc", "def"), put("ghi", "jkl"), put(null, "xyz"), containsKey(null), get(null, "xyz"),
                    remove("ghi", "jkl")
                ),
                (Comparator<String>) String::compareTo,
                Arrays.asList(
                    new SimpleEntry<>("abc", "def"),
                    new SimpleEntry<>(null, "xyz")
                )
            ),
            Arguments.of(
                List.of(),
                (Comparator<Integer>) Integer::compareTo,
                List.of()
            ),
            Arguments.of(
                Arrays.asList(
                    put(null, 1), get(null, 1), put(null, 10), get(null, 10), get(100, null), get(10, null),
                    get(1, null)
                ),
                (Comparator<Integer>) Integer::compareTo,
                List.of(
                    new SimpleEntry<>(null, 10)
                )
            ),
            Arguments.of(
                Arrays.asList(
                    put(new Stock("apple", 100), 5),
                    put(new Stock("yandex", 2), 0),
                    put(new Stock("Tinkoff", 10000), 1),
                    put(null, 50),
                    containsKey(null),
                    get(null, 50),
                    get(new Stock("apple", 100), 5),
                    remove(new Stock("yandex", 2), 0),
                    remove(null, 50)
                ),
                (Comparator<Stock>) Stock::compareTo,
                Arrays.asList(
                    new SimpleEntry<>(new Stock("apple", 100), 5),
                    new SimpleEntry<>(new Stock("Tinkoff", 10000), 1)
                )
            )
        );
    }
}
