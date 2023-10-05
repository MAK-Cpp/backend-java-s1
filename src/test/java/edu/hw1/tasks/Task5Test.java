package edu.hw1.tasks;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Task5Test {
    @Test
    @DisplayName("5) Проверка, является ли число или его потомок палиндромом")
    void testIsPalindromeDescendant() {
        // tests from HW1.md
        assertTrue(Task5.isPalindromeDescendant(11211230));
        assertTrue(Task5.isPalindromeDescendant(13001120));
        assertTrue(Task5.isPalindromeDescendant(23336014));
        assertTrue(Task5.isPalindromeDescendant(11));

        // my own tests
        assertTrue(Task5.isPalindromeDescendant(112233));
        assertFalse(Task5.isPalindromeDescendant(1000020000));
        assertFalse(Task5.isPalindromeDescendant(1234322));
        assertFalse(Task5.isPalindromeDescendant(9));
        assertFalse(Task5.isPalindromeDescendant(-12321));
    }
}
