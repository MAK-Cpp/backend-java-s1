package edu.hw1.tasks;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Task0Test {
    @Test
    @DisplayName("Через LOGGER на экране появляется \"Привет, мир!\"")
    void testPrintHelloWorld() throws Exception {
        String out = tapSystemOut(Task0::printHelloWorld);
        assertThat(out).contains("Привет, мир!");
    }
}
