package edu.hw1.tasks;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task6Test {
    @Test
    @DisplayName("6) Функция Капрекара меньше чем за 7 шагов преобразовывает 4-х значное число (кроме 1000) в 6174")
    void testCountK() {
        // tests from HW1.md
        assertEquals(3, Task6.countK(3524));
        assertEquals(5, Task6.countK(6621));
        assertEquals(4, Task6.countK(6554));
        assertEquals(3, Task6.countK(1234));

        // my own tests
        assertEquals(3, Task6.countK(3412));
        assertEquals(4, Task6.countK(1100));
        assertEquals(1, Task6.countK(7641));
        assertEquals(7, Task6.countK(2005));
    }
}
