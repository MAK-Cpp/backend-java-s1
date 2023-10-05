package edu.hw1.tasks;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task7Test {
    @Test
    @DisplayName("7) Бинарные циклические сдвиги")
    void testRotates() {
        // tests from HW1.md
        assertEquals(4, Task7.rotateRight(8, 1));
        assertEquals(1, Task7.rotateLeft(16, 1));
        assertEquals(6, Task7.rotateLeft(17, 2));

        // my own tests
        assertEquals(6, Task7.rotateLeft(17, 617));
        assertEquals(Integer.MAX_VALUE, Task7.rotateRight(Integer.MAX_VALUE, 100));
        assertEquals(Integer.MAX_VALUE, Task7.rotateLeft(Integer.MAX_VALUE, 100));
        assertEquals(83123450, Task7.rotateRight(83123450, 12609));
        assertEquals(1070791, Task7.rotateRight(1457173, 788));
    }
}
