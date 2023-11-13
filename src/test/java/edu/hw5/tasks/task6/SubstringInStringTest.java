package edu.hw5.tasks.task6;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Task 6")
class SubstringInStringTest {
    public static Stream<Arguments> testFalseSubstringInString() {
        return Stream.of(
            Arguments.of("abbbbbbbcccccddddd", "bbbbbbbcccccdddddd"),
            Arguments.of("ab", "ba")
        );
    }

    public static Stream<Arguments> testTrueSubstringInString() {
        return Stream.of(
            Arguments.of("abc", "achfdbaabgabcaabg"),
            Arguments.of("Hello", "Hello, world!"),
            Arguments.of("Kit", "Java Developer Kit")
        );
    }

    @ParameterizedTest
    @MethodSource
    void testTrueSubstringInString(final String inputString, final String inputText) {
        assertTrue(SubstringInString.check(inputString, inputText));
    }

    @ParameterizedTest
    @MethodSource
    void testFalseSubstringInString(final String inputString, final String inputText) {
        assertFalse(SubstringInString.check(inputString, inputText));
    }
}
