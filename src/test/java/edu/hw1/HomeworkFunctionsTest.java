package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class HomeworkFunctionsTest {
    @Test
    @DisplayName("Перевод времени формата mm:ss в секунды")
    void testMinutesToSeconds() {
        // tests from README.md
        assertEquals(HomeworkFunctions.minutesToSeconds("01:00"), 60);
        assertEquals(HomeworkFunctions.minutesToSeconds("13:56"), 836);
        assertEquals(HomeworkFunctions.minutesToSeconds("10:60"), -1);

        // my own tests
        assertEquals(HomeworkFunctions.minutesToSeconds("0:0"), 0);
        assertEquals(HomeworkFunctions.minutesToSeconds("95:59"), 5759);
        assertEquals(HomeworkFunctions.minutesToSeconds("1:23"), 83);

        // errors
        assertEquals(HomeworkFunctions.minutesToSeconds("-1:20"), -1);
        assertEquals(HomeworkFunctions.minutesToSeconds("20:-1"), -1);
        assertEquals(HomeworkFunctions.minutesToSeconds(""), -1);
        assertEquals(HomeworkFunctions.minutesToSeconds(":12"), -1);
        assertEquals(HomeworkFunctions.minutesToSeconds("31:"), -1);
        assertEquals(HomeworkFunctions.minutesToSeconds("123"), -1);
        assertEquals(HomeworkFunctions.minutesToSeconds("1:2:3"), -1);
        assertEquals(HomeworkFunctions.minutesToSeconds("Hello, world! :)"), -1);
    }

    @Test
    @DisplayName("Подсчет количества цифр в целом числе")
    void testCountDigits() {
        // tests from README.md
        assertEquals(HomeworkFunctions.countDigits(4666), 4);
        assertEquals(HomeworkFunctions.countDigits(544), 3);
        assertEquals(HomeworkFunctions.countDigits(0), 1);

        // my own tests
        assertEquals(HomeworkFunctions.countDigits(-1), 1);
        assertEquals(HomeworkFunctions.countDigits(Integer.MAX_VALUE), 10);
        assertEquals(HomeworkFunctions.countDigits(Integer.MIN_VALUE), 10);
    }

    @Test
    @DisplayName("Массив A можно вложить в массив B, если min(A) > min(B) && max(A) < max(B)")
    void testIsNestable() {
        // tests from README.md
        assertTrue(HomeworkFunctions.isNestable(new int[]{1, 2, 3, 4}, new int[]{0, 6}));
        assertTrue(HomeworkFunctions.isNestable(new int[]{3, 1}, new int[]{4, 0}));
        assertFalse(HomeworkFunctions.isNestable(new int[]{9, 9, 8}, new int[]{8, 9}));
        assertFalse(HomeworkFunctions.isNestable(new int[]{1, 2, 3, 4}, new int[]{2, 3}));

        // my own tests
        assertFalse(HomeworkFunctions.isNestable(new int[]{}, new int[]{1, 2, 3}));
        assertFalse(HomeworkFunctions.isNestable(new int[]{1, 2, 3}, new int[]{}));
        assertFalse(HomeworkFunctions.isNestable(new int[]{}, new int[]{}));

    }
}
