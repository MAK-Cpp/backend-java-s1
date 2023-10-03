package edu.hw1;

import edu.hw1.tasks.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HomeworkTasksTest {
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

    @Test
    @DisplayName("4) Починка строки, в которой S[i] <-> S[i + 1]")
    void testFixString() {
        // tests from HW1.md
        assertEquals("214365", Task4.fixString("123456"));
        assertEquals("This is a mixed up string.", Task4.fixString("hTsii  s aimex dpus rtni.g"));
        assertEquals("abcde", Task4.fixString("badce"));

        // my own tests
        assertEquals("", Task4.fixString(""));
        assertEquals("A", Task4.fixString("A"));
        assertEquals("Hello, world!", Task4.fixString("eHll,ow rodl!"));
    }

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

    @Test
    @DisplayName("8) Валидность расстановки коней на поле")
    void testKnightBoardCapture() {
        // tests from HW1.md
        assertTrue(Task8.knightBoardCapture(new int[][] {
            {0, 0, 0, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 1, 0, 1, 0},
            {0, 1, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 1, 0, 0, 0}
        }));
        assertFalse(Task8.knightBoardCapture(new int[][] {
            {1, 0, 1, 0, 1, 0, 1, 0},
            {0, 1, 0, 1, 0, 1, 0, 1},
            {0, 0, 0, 0, 1, 0, 1, 0},
            {0, 0, 1, 0, 0, 1, 0, 1},
            {1, 0, 0, 0, 1, 0, 1, 0},
            {0, 0, 0, 0, 0, 1, 0, 1},
            {1, 0, 0, 0, 1, 0, 1, 0},
            {0, 0, 0, 1, 0, 1, 0, 1}
        }));
        assertFalse(Task8.knightBoardCapture(new int[][] {
            {0, 0, 0, 0, 1, 0, 0, 0},
            {0, 0, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 1, 0, 0, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 1, 0, 0, 0},
            {0, 0, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 0, 1, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 0}
        }));

        // my own tests
        assertTrue(Task8.knightBoardCapture(new int[][] {
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0}
        }));
        assertFalse(Task8.knightBoardCapture(new int[][] {
            {1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1}
        }));
        assertTrue(Task8.knightBoardCapture(new int[][] {
            {1, 1, 1, 1, 1, 1, 1, 1},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {1, 1, 1, 1, 1, 1, 1, 1},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {1, 1, 1, 1, 1, 1, 1, 1},
            {0, 0, 0, 0, 0, 0, 0, 0}
        }));
        assertTrue(Task8.knightBoardCapture(new int[][] {
            {1, 1, 0, 0, 0, 0, 1, 1},
            {1, 1, 0, 0, 0, 0, 1, 1},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {1, 1, 0, 0, 0, 0, 1, 1},
            {1, 1, 0, 0, 0, 0, 1, 1}
        }));
        assertFalse(Task8.knightBoardCapture(new int[][] {
            {1, 0, 0, 0, 0, 0, 0, 1},
            {0, 1, 0, 0, 0, 0, 1, 0},
            {0, 0, 1, 0, 0, 1, 0, 0},
            {0, 0, 0, 1, 1, 0, 0, 0},
            {0, 0, 0, 1, 1, 0, 0, 0},
            {0, 0, 1, 0, 0, 1, 0, 0},
            {0, 1, 0, 0, 0, 0, 1, 0},
            {1, 0, 0, 0, 0, 0, 0, 1}
        }));
    }
}
