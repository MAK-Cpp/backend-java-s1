package edu.hw3.tasks.task2;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static edu.hw3.tasks.task2.ClusteringBrackets.clusterize;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ClusteringBracketsTest {
    private static final String CLOSE_BRACKET_ERROR = "expected ')', got nothing";
    private static final String OPEN_BRACKET_ERROR = "expected '(', got nothing";
    private static final String WRONG_CHARACTER_ERROR = "expected '(' or ')', got ";

    @ParameterizedTest
    @MethodSource("correctBracketSequences")
    public void testCorrectClusteringBrackets(final String sequence, final String[] elements) {
        assertThat(clusterize(sequence)).isEqualTo(elements);
    }

    @ParameterizedTest
    @MethodSource("incorrectBracketSequences")
    public void testIncorrectClusteringBrackets(final String sequence, final String errorMessage) {
        final BracketSequenceException exception =
            assertThrows(BracketSequenceException.class, () -> clusterize(sequence));
        assertThat(exception.getMessage()).isEqualTo(errorMessage);
    }

    private static Stream<Arguments> correctBracketSequences() {
        return Stream.of(
            Arguments.of("()()()", new String[] {"()", "()", "()"}),
            Arguments.of("((()))", new String[] {"((()))"}),
            Arguments.of("((()))(())()()(()())", new String[] {"((()))", "(())", "()", "()", "(()())"}),
            Arguments.of("((())())(()(()()))", new String[] {"((())())", "(()(()()))"}),
            Arguments.of("", new String[0]),
            Arguments.of(
                "()(())((()))(((())))((((()))))",
                new String[] {"()", "(())", "((()))", "(((())))", "((((()))))"}
            )
        );
    }

    public static Stream<Arguments> incorrectBracketSequences() {
        return Stream.of(
            Arguments.of("(((", CLOSE_BRACKET_ERROR),
            Arguments.of(")))", OPEN_BRACKET_ERROR),
            Arguments.of("(1)", WRONG_CHARACTER_ERROR + '1'),
            Arguments.of("((())(())()()(()())", CLOSE_BRACKET_ERROR),
            Arguments.of("((())())(()()()))", OPEN_BRACKET_ERROR),
            Arguments.of("(x + 1) * 2 + (y + 3) * 4 = 100", WRONG_CHARACTER_ERROR + 'x')
        );
    }
}
