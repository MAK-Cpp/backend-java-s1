package edu.hw1.tasks;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Task3Test {
    @Test
    @DisplayName("3) Массив A можно вложить в массив B, если min(A) > min(B) && max(A) < max(B)")
    void testIsNestable() {
        // tests from HW1.md
        assertTrue(Task3.isNestable(new int[] {1, 2, 3, 4}, new int[] {0, 6}));
        assertTrue(Task3.isNestable(new int[] {3, 1}, new int[] {4, 0}));
        assertFalse(Task3.isNestable(new int[] {9, 9, 8}, new int[] {8, 9}));
        assertFalse(Task3.isNestable(new int[] {1, 2, 3, 4}, new int[] {2, 3}));

        // my own tests
        assertFalse(Task3.isNestable(new int[] {}, new int[] {1, 2, 3}));
        assertFalse(Task3.isNestable(new int[] {1, 2, 3}, new int[] {}));
        assertFalse(Task3.isNestable(new int[] {}, new int[] {}));
        assertTrue(Task3.isNestable(new int[] {-10, -20, -30}, new int[] {-100, -1, 0, 1, 100}));
    }
}
