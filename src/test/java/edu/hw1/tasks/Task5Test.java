package edu.hw1.tasks;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.IntStream;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Task5Test {
    @ParameterizedTest
    @MethodSource("trueArgs")
    @DisplayName("Число или его потомок является палиндромом")
    void testIsPalindromeDescendantTrue(final int input) {
        assertTrue(Task5.isPalindromeDescendant(input));
    }

    @ParameterizedTest
    @MethodSource("falseArgs")
    @DisplayName("Ни число, ни его потомки не являются палиндромом или число не подходит под условие")
    void testIsPalindromeDescendantFalse(final int input) {
        assertFalse(Task5.isPalindromeDescendant(input));
    }

    private static IntStream trueArgs() {
        return IntStream.of(11211230, 13001120, 23336014, 11, 112233);
    }

    private static IntStream falseArgs() {
        return IntStream.of(1000020000, 1234322, 9, -12321);
    }
}
