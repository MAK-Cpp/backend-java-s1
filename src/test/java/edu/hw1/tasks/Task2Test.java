package edu.hw1.tasks;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task2Test {
    @Test
    @DisplayName("2) Подсчет количества цифр в целом числе")
    void testCountDigits() {
        // tests from HW1.md
        assertEquals(4, Task2.countDigits(4666));
        assertEquals(3, Task2.countDigits(544));
        assertEquals(1, Task2.countDigits(0));

        // my own tests
        assertEquals(1, Task2.countDigits(-1), 1);
        assertEquals(10, Task2.countDigits(Integer.MAX_VALUE));
        assertEquals(10, Task2.countDigits(Integer.MIN_VALUE));
    }
}
