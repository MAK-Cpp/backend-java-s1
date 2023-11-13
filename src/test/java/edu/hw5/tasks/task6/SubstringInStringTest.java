package edu.hw5.tasks.task6;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Task 6")
class SubstringInStringTest {
    public static Stream<Arguments> testSubstringInString() {
        return Stream.of(
            Arguments.of("abc", "achfdbaabgabcaabg", true),
            Arguments.of("abbbbbbbcccccddddd", "bbbbbbbcccccdddddd", false),
            Arguments.of("ab", "ba", false),
            Arguments.of("Hello", "Hello, world!", true),
            Arguments.of("Kit", "Java Developer Kit", true)
        );
    }

    @ParameterizedTest
    @MethodSource
    void testSubstringInString(final String inputString, final String inputText, final boolean output) {
        assertThat(SubstringInString.check(inputString, inputText)).isEqualTo(output);
    }
}
