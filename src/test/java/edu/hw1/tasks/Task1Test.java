package edu.hw1.tasks;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task1Test {
    @Test
    @DisplayName("1) Перевод времени формата mm:ss в секунды")
    void testMinutesToSeconds() {
        // tests from HW1.md
        assertEquals(60, Task1.minutesToSeconds("01:00"));
        assertEquals(836, Task1.minutesToSeconds("13:56"));
        assertEquals(-1, Task1.minutesToSeconds("10:60"));

        // my own tests
        assertEquals(0, Task1.minutesToSeconds("0:0"));
        assertEquals(5759, Task1.minutesToSeconds("95:59"));
        assertEquals(83, Task1.minutesToSeconds("1:23"));

        // errors
        assertEquals(-1, Task1.minutesToSeconds("-1:20"));
        assertEquals(-1, Task1.minutesToSeconds("20:-1"));
        assertEquals(-1, Task1.minutesToSeconds(""));
        assertEquals(-1, Task1.minutesToSeconds(":12"));
        assertEquals(-1, Task1.minutesToSeconds("31:"));
        assertEquals(-1, Task1.minutesToSeconds("123"));
        assertEquals(-1, Task1.minutesToSeconds("1:2:3"));
        assertEquals(-1, Task1.minutesToSeconds("Hello, world! :)"));
    }
}
