package edu.hw7.tasks.task4;

import edu.prettyTable.line.DoubleLine;
import edu.prettyTable.line.LongLine;
import edu.prettyTable.table.ColumnTypedTable;
import edu.prettyTable.table.Table;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import static edu.hw7.tasks.task4.MonteCarloMethod.pi;

@DisplayName("Test 4")
class MonteCarloMethodTest {
    private static final int CORE_COUNTS = Runtime.getRuntime().availableProcessors();
    private static final Table SPEED_TABLE = new ColumnTypedTable(
        "Скорость работы однопоточного и многопоточного методов Монте-Карло",
        "Кол-во симуляций",
        new LongLine("1 поток (наносекунды)"),
        new LongLine(CORE_COUNTS + " потоков (наносекунды)")
    );
    private static final Table INACCURACY_TABLE = new ColumnTypedTable(
        "Погрешность метода Монте-Карло в зависимости от кол-ва симуляций",
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
            100_000_000
//            1_000_000_000
        );
    }

    @ParameterizedTest
    @MethodSource
    void testMonteCarloMethod(int n) {
        long start1 = System.nanoTime();
        double oneCorePi = pi(n);
        long end1 = System.nanoTime();
        long start2 = System.nanoTime();
        double fewCoresPi = pi(n, CORE_COUNTS);
        long end2 = System.nanoTime();
        SPEED_TABLE.addRow(new LongLine(Integer.toString(n), end1 - start1, end2 - start2));
        INACCURACY_TABLE.addRow(new DoubleLine(Integer.toString(n), Math.abs(Math.PI - fewCoresPi) / Math.PI * 100));
    }

    @AfterAll
    static void printTables() {
        System.out.println(SPEED_TABLE);
        System.out.println();
        System.out.println(INACCURACY_TABLE);
    }
}

/*
+--------------------------------------------------------------------+
| Скорость работы однопоточного и многопоточного методов Монте-Карло |
+--------------------------------------------------------------------+
[]==================[]==============[]=============[]
|| Кол-во симуляций ||   1 поток    ||  8 потоков  ||
[]==================[]==============[]=============[]
||        10        ||   15869042   ||   967750    ||
||       100        ||   1623916    ||   808958    ||
||       1000       ||   3769833    ||   1787375   ||
||      10000       ||   19460333   ||   3066750   ||
||      100000      ||   60046208   ||  27532125   ||
||     1000000      ||  264011875   ||  76945583   ||
||     10000000     ||  2602900708  ||  549269583  ||
||    100000000     || 25654351708  || 5532615500  ||
||    1000000000    || 256303503500 || 55012285417 ||
[]==================[]==============[]=============[]

+------------------------------------------------------------------+
| Погрешность метода Монте-Карло в зависимости от кол-ва симуляций |
+------------------------------------------------------------------+
[]==================[]=======================[]
|| Кол-во симуляций ||      Погрешность (%)  ||
[]==================[]=======================[]
||        10        ||  2.8385786132246844   ||
||       100        ||  1.8591635788130243   ||
||       1000       ||  0.6530579444312443   ||
||      10000       ||  0.21535004152560114  ||
||      100000      || 0.026947468870980297  ||
||     1000000      ||  0.06871806265742335  ||
||     10000000     || 0.020473902844171433  ||
||    100000000     || 0.0033636585775869664 ||
||    1000000000    || 0.0002072106858314878 ||
[]==================[]=======================[]

*/
