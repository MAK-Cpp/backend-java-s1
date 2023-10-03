package edu.hw1;

import edu.hw1.tasks.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HomeworkTasksTest {
    @Test
    @DisplayName("Перевод времени формата mm:ss в секунды")
    void testMinutesToSeconds() {
        // tests from HW1.md
        assertEquals(Task1.minutesToSeconds("01:00"), 60);
        assertEquals(Task1.minutesToSeconds("13:56"), 836);
        assertEquals(Task1.minutesToSeconds("10:60"), -1);

        // my own tests
        assertEquals(Task1.minutesToSeconds("0:0"), 0);
        assertEquals(Task1.minutesToSeconds("95:59"), 5759);
        assertEquals(Task1.minutesToSeconds("1:23"), 83);

        // errors
        assertEquals(Task1.minutesToSeconds("-1:20"), -1);
        assertEquals(Task1.minutesToSeconds("20:-1"), -1);
        assertEquals(Task1.minutesToSeconds(""), -1);
        assertEquals(Task1.minutesToSeconds(":12"), -1);
        assertEquals(Task1.minutesToSeconds("31:"), -1);
        assertEquals(Task1.minutesToSeconds("123"), -1);
        assertEquals(Task1.minutesToSeconds("1:2:3"), -1);
        assertEquals(Task1.minutesToSeconds("Hello, world! :)"), -1);
    }

    @Test
    @DisplayName("Подсчет количества цифр в целом числе")
    void testCountDigits() {
        // tests from HW1.md
        assertEquals(Task2.countDigits(4666), 4);
        assertEquals(Task2.countDigits(544), 3);
        assertEquals(Task2.countDigits(0), 1);

        // my own tests
        assertEquals(Task2.countDigits(-1), 1);
        assertEquals(Task2.countDigits(Integer.MAX_VALUE), 10);
        assertEquals(Task2.countDigits(Integer.MIN_VALUE), 10);
    }

    @Test
    @DisplayName("Массив A можно вложить в массив B, если min(A) > min(B) && max(A) < max(B)")
    void testIsNestable() {
        // tests from HW1.md
        assertTrue(Task3.isNestable(new int[]{1, 2, 3, 4}, new int[]{0, 6}));
        assertTrue(Task3.isNestable(new int[]{3, 1}, new int[]{4, 0}));
        assertFalse(Task3.isNestable(new int[]{9, 9, 8}, new int[]{8, 9}));
        assertFalse(Task3.isNestable(new int[]{1, 2, 3, 4}, new int[]{2, 3}));

        // my own tests
        assertFalse(Task3.isNestable(new int[]{}, new int[]{1, 2, 3}));
        assertFalse(Task3.isNestable(new int[]{1, 2, 3}, new int[]{}));
        assertFalse(Task3.isNestable(new int[]{}, new int[]{}));
        assertTrue(Task3.isNestable(new int[]{-10, -20, -30}, new int[]{-100, -1, 0, 1, 100}));
    }

    @Test
    @DisplayName("Починка строки, в которой S[i] <-> S[i + 1]")
    void testFixString() {
        // tests from HW1.md
        assertEquals(Task4.fixString("123456"), "214365");
        assertEquals(Task4.fixString("hTsii  s aimex dpus rtni.g"), "This is a mixed up string.");
        assertEquals(Task4.fixString("badce"), "abcde");

        // my own tests
        assertEquals(Task4.fixString(""), "");
        assertEquals(Task4.fixString("A"), "A");
        assertEquals(Task4.fixString("eHll,ow rodl!"), "Hello, world!");
    }

    @Test
    @DisplayName("Проверка, является ли число или его потомок палиндромом")
    void testIsPalindromeDescendant() {
        // tests from HW1.md
        assertTrue(Task5.isPalindromeDescendant(11211230));
        assertTrue(Task5.isPalindromeDescendant(13001120));
        assertTrue(Task5.isPalindromeDescendant(23336014));
        assertTrue(Task5.isPalindromeDescendant(11));

        // my own tests
        assertTrue(Task5.isPalindromeDescendant(112233));
    }

    @Test
    @DisplayName("Функция Капрекара меньше чем за 7 шагов преобразовывает 4-х значное число (кроме 1000) в 6174")
    void testCountK() {
        // tests from HW1.md
        assertEquals(Task6.countK(3524), 3);
        assertEquals(Task6.countK(6621), 5);
        assertEquals(Task6.countK(6554), 4);
        assertEquals(Task6.countK(1234), 3);


    }
}
