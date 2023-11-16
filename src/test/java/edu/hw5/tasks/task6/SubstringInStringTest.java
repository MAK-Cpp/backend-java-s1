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
            Arguments.of("ab", "ba"),
            Arguments.of("^(^1$)|(^((1((1((1[01])|0))|0))|0)[01]*$)$", "Regular expression for task 'any string other than 11 or 111' can match 1001 and 100 and 1 and 110 and 101 and 100001101010101101010111"),
            Arguments.of("^(^1$)|(^((1((1((1[01])|0))|0))|0)[01]*$)$", "100001101010101101010111"),
            Arguments.of("^(^1$)|(^((1((1((1[01])|0))|0))|0)[01]*$)$", "111"),
            Arguments.of("^(^1$)|(^((1((1((1[01])|0))|0))|0)[01]*$)$", "11")
        );
    }

    public static Stream<Arguments> testTrueSubstringInString() {
        return Stream.of(
            Arguments.of("abc", "achfdbaabgabcaabg"),
            Arguments.of("Hello", "Hello, world!"),
            Arguments.of("Kit", "Java Developer Kit"),
            Arguments.of(
                "^(^1$)|(^((1((1((1[01])|0))|0))|0)[01]*$)$",
                "Regular expression for task 'any string other than 11 or 111' is ^(^1$)|(^((1((1((1[01])|0))|0))|0)[01]*$)$, and i think it works"
            )
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
