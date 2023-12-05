package edu.hw8.tasks.task3;

import edu.prettyTable.line.DoubleLine;
import edu.prettyTable.table.ColumnTypedTable;
import edu.prettyTable.table.Table;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.Map;
import java.util.stream.Stream;
import static edu.hw8.tasks.task3.DecipherMD5.fastSolution;
import static edu.hw8.tasks.task3.DecipherMD5.slowSolution;

@DisplayName("Task 3")
class DecipherMD5Test {
    private static final int CORE_COUNTS = Runtime.getRuntime().availableProcessors();
    private static final Table SPEED_TABLE = new ColumnTypedTable(
        "Скорость работы однопоточного и многопоточного методов",
        "Длина пароля",
        new DoubleLine("1 поток, время (секунды)"),
        new DoubleLine(CORE_COUNTS + " потоков (секунды)")
    );

    public static Stream<Arguments> testDecipher() {
        return Stream.of(
            Arguments.of(
                3,
                Map.of(
                    "1b761a9f2db00bdccfaccdec050cc732", "a.v.petrov",
                    "87922b7db4e70dd304cc7e84f8762a29", "v.v.belov ",
                    "555d6702c950ecb729a966504af0a635", "a.s.ivanov",
                    "7de13dd362a23575e036d9de55f86e1d", "k.p.maslov"
                )
            ),
            Arguments.of(
                4,
                Map.of(
                    "051f7661ee7c287be66ee572768ec8f5", "a.v.petrov",
                    "87922b7db4e70dd304cc7e84f8762a29", "v.v.belov ",
                    "5531a5834816222280f20d1ef9e95f69", "a.s.ivanov",
                    "d31594c486e0ed29e7adbb0340f2a5e5", "k.p.maslov"
                )
            ),
            Arguments.of(
                5,
                Map.of(
                    "8b1a9953c4611296a827abf8c47804d7", "a.v.petrov",
                    "8042e486e5ed2d147ccd4bd0e5ef4918", "v.v.belov ",
                    "b4c12630a112a477a934912a92329614", "a.s.ivanov",
                    "7d53cbd3f0c0e2af31c3ba1eab940f22", "k.p.maslov"
                )
            )
        );
    }

    @ParameterizedTest
    @MethodSource
    void testDecipher(int length, Map<String, String> database) {
        long start1 = System.nanoTime();
        slowSolution(database);
        long end1 = System.nanoTime();
        long start2 = System.nanoTime();
        fastSolution(database);
        long end2 = System.nanoTime();
        SPEED_TABLE.addRow(Integer.toString(length), (end1 - start1) / 1e9, (end2 - start2) / 1e9);
    }

    @AfterAll
    static void printTable() {
        System.out.println(SPEED_TABLE);
    }
}
