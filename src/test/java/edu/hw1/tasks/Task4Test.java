package edu.hw1.tasks;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task4Test {
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
}
