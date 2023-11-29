package edu.hw7.tasks.task4;

import edu.prettyTable.line.DoubleLine;
import edu.prettyTable.table.ColumnTypedTable;
import edu.prettyTable.table.Table;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.IntStream;
import static edu.hw7.tasks.task4.MonteCarloMethod.fastPi;
import static edu.hw7.tasks.task4.MonteCarloMethod.pi;

@DisplayName("Test 4")
class MonteCarloMethodTest {
    private static final int CORE_COUNTS = Runtime.getRuntime().availableProcessors();
    private static final Table SPEED_TABLE = new ColumnTypedTable(
        "Скорость работы однопоточного и многопоточного методов Монте-Карло",
        "Кол-во симуляций",
        new DoubleLine("1 поток, значение"),
        new DoubleLine("1 поток, время (секунды)"),
        new DoubleLine(CORE_COUNTS + " потоков, значение"),
        new DoubleLine(CORE_COUNTS + " потоков (секунды)")
    );
    private static final Table INACCURACY_TABLE = new ColumnTypedTable(
        "Погрешность метода Монте-Карло в зависимости от кол-ва симуляций (в сравнении с Math.PI)",
        "Кол-во симуляций",
        new DoubleLine("Погрешность (%)")
    );

    public static IntStream testMonteCarloMethod() {
        return IntStream.of(
            10,
            100,
            1_000,
            10_000,
            100_000,
            1_000_000,
            10_000_000,
            100_000_000,
            1_000_000_000
        );
    }

    @ParameterizedTest
    @MethodSource
    void testMonteCarloMethod(int n) {
        long start1 = System.nanoTime();
        double oneCorePi = pi(n);
        long end1 = System.nanoTime();
        long start2 = System.nanoTime();
        double fewCoresPi = fastPi(n);
        long end2 = System.nanoTime();
        SPEED_TABLE.addRow(Integer.toString(n), oneCorePi, (end1 - start1) / 1e9, fewCoresPi, (end2 - start2) / 1e9);
        INACCURACY_TABLE.addRow(new DoubleLine(Integer.toString(n), Math.abs(Math.PI - fewCoresPi) / Math.PI * 100));
    }

    @AfterAll
    static void printTables() {
        System.out.println(SPEED_TABLE);
        System.out.println();
        System.out.println(INACCURACY_TABLE);
    }
}
