package edu.project4.fractalFlame;

import edu.prettyTable.line.DoubleLine;
import edu.prettyTable.table.ColumnTypedTable;
import edu.prettyTable.table.Table;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Stream;

@DisplayName("Project 4 speed test")
class FractalFlameGeneratorTest {
    private static final Table SPEED_TABLE = new ColumnTypedTable(
        "Сравнение скорости работы одно- и много- поточных вариантов генерации фрактального пламени",
        "Вариация(-ии)",
        new DoubleLine("1 поток, сек"),
        new DoubleLine(Runtime.getRuntime().availableProcessors() + " поток(ов), сек")
    );

    public static Stream<Arguments> testSpeed() {
        return Stream.of(
            Arguments.of(Set.of(Variation.V0), "V0"),
            Arguments.of(Set.of(Variation.V2), "V2"),
            Arguments.of(Set.of(Variation.V10), "V10"),
            Arguments.of(Set.of(Variation.V20), "V20"),
            Arguments.of(Set.of(Variation.V21), "V21")
        );
    }

    private static double getSeconds(long start, long finish) {
        return (finish - start) / 1e9;
    }

    @ParameterizedTest
    @MethodSource
    void testSpeed(Set<Variation> variationSet, String variationNames) {
        FractalFlameGenerator generator = new FractalFlameGenerator(
            100000,
            10,
            variationSet,
            new ImageSettings(1920, 1080, Path.of(""), "fractal flame", ImageFormat.PNG),
            new AffineTransform(1, 0, 0, 0, 1, 0),
            1,
            1000
        );
        long slowStart = System.nanoTime();
        generator.generate();
        long slowFinish = System.nanoTime();
        long quickStart = System.nanoTime();
        generator.fastGenerate();
        long quickFinish = System.nanoTime();
        SPEED_TABLE.addRow(variationNames, getSeconds(slowStart, slowFinish), getSeconds(quickStart, quickFinish));
    }

    @AfterAll
    static void printTable() {
        System.out.println(SPEED_TABLE);
    }
}
