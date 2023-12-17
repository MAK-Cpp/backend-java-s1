package edu.hw10.tasks.task1;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

class RandomObjectGeneratorTest {
    private static final RandomObjectGenerator GENERATOR = new RandomObjectGenerator();

    public static Stream<Arguments> byConstructor() {
        return Stream.of(
            Arguments.of(Person.class),
            Arguments.of(Car.class)
        );
    }

    public static Stream<Arguments> byFabric() {
        return Stream.of(
            Arguments.of(Person.class, "born"),
            Arguments.of(Car.class, "newBMW")
        );
    }

    @ParameterizedTest
    @MethodSource("byConstructor")
    void testRandomObjectGenerator(Class<?> type) {
        var output = GENERATOR.nextObject(type);
        System.out.println(output);
    }

    @ParameterizedTest
    @MethodSource("byFabric")
    void testRandomObjectGenerator(Class<?> type, String name) {
        var output = GENERATOR.nextObject(type, name);
        System.out.println(output);
    }
}
