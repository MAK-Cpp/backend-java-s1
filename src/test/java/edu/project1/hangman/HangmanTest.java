package edu.project1.hangman;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemErrNormalized;
import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOutNormalized;
import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;
import static edu.project1.hangman.GameStatements.*;
import static edu.project1.hangman.Hangman.getLengthErrorMessage;
import static edu.project1.hangman.Hangman.getNotALetterErrorMessage;
import static edu.project1.hangman.Hangman.createOutput;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static edu.project1.hangman.Hangman.getRepeatedLetterErrorMessage;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HangmanTest {
    @ParameterizedTest
    @MethodSource("wrongDictionaries")
    void testHangmanWithWrongDictionary(final CorrectDictionary wrongDictionary) {
        Hangman game = new Hangman(wrongDictionary, new TestPlayer(null, 0));
        HangmanException exception = assertThrows(HangmanException.class, game::play);
        assertThat(exception.getMessage()).isEqualTo("dictionary contains not allowed word(s)");
    }

    @ParameterizedTest
    @MethodSource("normalGameFull")
    void testHangmanNormalGameFull(final CorrectDictionary dictionary, final Player player, final String[] statements)
        throws Exception {
        final Hangman game = new Hangman(dictionary, player);
        final String[] lines = tapSystemOutNormalized(game::play).split("\n");
        for (int statementId = 0; statementId < statements.length; statementId++) {
            StringBuilder result = new StringBuilder(600);
            for (int i = statementId * IMAGE_HEIGHT; i < (statementId + 1) * IMAGE_HEIGHT; i++) {
                result.append(lines[i]).append("\n");
            }
            assertThat(result.toString()).isEqualTo(statements[statementId]);
        }
    }

    @ParameterizedTest
    @MethodSource("gameWithWrongGuesses")
    void testHangmanGameWithWrongGuesses(final CorrectDictionary dictionary, final Player player, final String[] errors)
        throws Exception {
        final Hangman game = new Hangman(dictionary, player);
        final String[] errorLines = tapSystemErrNormalized(game::play).split("\n");
        for (int i = 0; i < errorLines.length; i++) {
            assertThat(errorLines[i]).isEqualTo(errors[i]);
        }
    }

    private static CorrectDictionary createWrongDictionary(final String wrongResult) {
        return new CorrectDictionary() {
            @Override
            @NotNull String getRandomWordWithoutCheck() {
                return wrongResult;
            }
        };
    }

    private static Stream<CorrectDictionary> wrongDictionaries() {
        return Stream.of(
            createWrongDictionary("123"),
            createWrongDictionary(" \s\n\t"),
            createWrongDictionary("hello, world!"),
            createWrongDictionary("Hi)"),
            createWrongDictionary("1sth4ty0u")
        );
    }

    private static String HangmanOutput(final GameStatements statement, final int countMistakes, final String hiddenWord, final String hiddenWordForUser) {
        return createOutput(statement, countMistakes, hiddenWord, hiddenWordForUser).toString();
    }

    private static Stream<Arguments> normalGameFull() {
        return Stream.of(
            Arguments.of(new TestDictionary(0), new TestPlayer(new String[]{"t", "E", "s", "w", "o", "r", "y", "d"},0), new String[] {
                HangmanOutput(START, 0, "testword", "? ? ? ? ? ? ? ? "),
                HangmanOutput(START, 0, "testword", "t ? ? t ? ? ? ? "),
                HangmanOutput(START, 0, "testword", "t e ? t ? ? ? ? "),
                HangmanOutput(START, 0, "testword", "t e s t ? ? ? ? "),
                HangmanOutput(START, 0, "testword", "t e s t w ? ? ? "),
                HangmanOutput(START, 0, "testword", "t e s t w o ? ? "),
                HangmanOutput(START, 0, "testword", "t e s t w o r ? "),
                HangmanOutput(FIRST_MISTAKE, 1, "testword", "t e s t w o r ? "),
                HangmanOutput(WIN, 1, "testword", "t e s t w o r d ")
            }),
            Arguments.of(new TestDictionary(0), new TestPlayer(new String[]{"a", "b", "c", "f", "g"},0), new String[] {
                HangmanOutput(START, 0, "testword", "? ? ? ? ? ? ? ? "),
                HangmanOutput(FIRST_MISTAKE, 1, "testword", "? ? ? ? ? ? ? ? "),
                HangmanOutput(SECOND_MISTAKE, 2, "testword", "? ? ? ? ? ? ? ? "),
                HangmanOutput(THIRD_MISTAKE, 3, "testword", "? ? ? ? ? ? ? ? "),
                HangmanOutput(FOURTH_MISTAKE, 4, "testword", "? ? ? ? ? ? ? ? "),
                HangmanOutput(LOSE, 5, "testword", "t e s t w o r d ")
            }),
            Arguments.of(new TestDictionary(0), new TestPlayer(new String[]{"T", "x", "E", "coNceDE"},0), new String[] {
                HangmanOutput(START, 0, "testword", "? ? ? ? ? ? ? ? "),
                HangmanOutput(START, 0, "testword", "t ? ? t ? ? ? ? "),
                HangmanOutput(FIRST_MISTAKE, 1, "testword", "t ? ? t ? ? ? ? "),
                HangmanOutput(FIRST_MISTAKE, 1, "testword", "t e ? t ? ? ? ? "),
                HangmanOutput(CONCEDE, 1, "testword", "t e s t w o r d ")
            }),
            Arguments.of(new TestDictionary(1), new TestPlayer(new String[]{"A", "B", "C", "D", "E", "F", "R"}, 0), new String[] {
                HangmanOutput(START, 0, "abracadabra", "? ? ? ? ? ? ? ? ? ? ? "),
                HangmanOutput(START, 0, "abracadabra", "a ? ? a ? a ? a ? ? a "),
                HangmanOutput(START, 0, "abracadabra", "a b ? a ? a ? a b ? a "),
                HangmanOutput(START, 0, "abracadabra", "a b ? a c a ? a b ? a "),
                HangmanOutput(START, 0, "abracadabra", "a b ? a c a d a b ? a "),
                HangmanOutput(FIRST_MISTAKE, 1, "abracadabra", "a b ? a c a d a b ? a "),
                HangmanOutput(SECOND_MISTAKE, 2, "abracadabra", "a b ? a c a d a b ? a "),
                HangmanOutput(WIN, 2, "abracadabra", "a b r a c a d a b r a ")
            }),
            Arguments.of(new TestDictionary(1), new TestPlayer(new String[]{}, 0), new String[] {
                HangmanOutput(START, 0, "abracadabra", "? ? ? ? ? ? ? ? ? ? ? "),
                HangmanOutput(CONCEDE, 0, "abracadabra", "a b r a c a d a b r a ")
            }),
            Arguments.of(new TestDictionary(2), new TestPlayer(new String[]{"h", "s", "e", "m", "i", "p", "t", "o"}, 0), new String[] {
                HangmanOutput(START, 0, "itmo", "? ? ? ? "),
                HangmanOutput(FIRST_MISTAKE, 1, "itmo", "? ? ? ? "),
                HangmanOutput(SECOND_MISTAKE, 2, "itmo", "? ? ? ? "),
                HangmanOutput(THIRD_MISTAKE, 3, "itmo", "? ? ? ? "),
                HangmanOutput(THIRD_MISTAKE, 3, "itmo", "? ? m ? "),
                HangmanOutput(THIRD_MISTAKE, 3, "itmo", "i ? m ? "),
                HangmanOutput(FOURTH_MISTAKE, 4, "itmo", "i ? m ? "),
                HangmanOutput(FOURTH_MISTAKE, 4, "itmo", "i t m ? "),
                HangmanOutput(WIN, 4, "itmo", "i t m o ")
            }),
            Arguments.of(new TestDictionary(2), new TestPlayer(new String[]{"o", "A", "i", "t", "M", "O"}, 0), new String[] {
                HangmanOutput(START, 0, "itmo", "? ? ? ? "),
                HangmanOutput(START, 0, "itmo", "? ? ? o "),
                HangmanOutput(FIRST_MISTAKE, 1, "itmo", "? ? ? o "),
                HangmanOutput(FIRST_MISTAKE, 1, "itmo", "i ? ? o "),
                HangmanOutput(FIRST_MISTAKE, 1, "itmo", "i t ? o "),
                HangmanOutput(WIN, 1, "itmo", "i t m o ")
            }),
            Arguments.of(new TestDictionary(0), new TestPlayer(new String[]{
                "z", "v", "concede",
                "a", "b", "c", "t", "e", "s", "w", "o", "r", "d",
                "a", "z", "v", "b", "c"}, 2), new String[] {
                HangmanOutput(START, 0, "testword", "? ? ? ? ? ? ? ? "),
                HangmanOutput(FIRST_MISTAKE, 1, "testword", "? ? ? ? ? ? ? ? "),
                HangmanOutput(SECOND_MISTAKE, 2, "testword", "? ? ? ? ? ? ? ? "),
                HangmanOutput(CONCEDE, 2, "testword", "t e s t w o r d "),
                HangmanOutput(START, 0, "testword", "? ? ? ? ? ? ? ? "),
                HangmanOutput(FIRST_MISTAKE, 1, "testword", "? ? ? ? ? ? ? ? "),
                HangmanOutput(SECOND_MISTAKE, 2, "testword", "? ? ? ? ? ? ? ? "),
                HangmanOutput(THIRD_MISTAKE, 3, "testword", "? ? ? ? ? ? ? ? "),
                HangmanOutput(THIRD_MISTAKE, 3, "testword", "t ? ? t ? ? ? ? "),
                HangmanOutput(THIRD_MISTAKE, 3, "testword", "t e ? t ? ? ? ? "),
                HangmanOutput(THIRD_MISTAKE, 3, "testword", "t e s t ? ? ? ? "),
                HangmanOutput(THIRD_MISTAKE, 3, "testword", "t e s t w ? ? ? "),
                HangmanOutput(THIRD_MISTAKE, 3, "testword", "t e s t w o ? ? "),
                HangmanOutput(THIRD_MISTAKE, 3, "testword", "t e s t w o r ? "),
                HangmanOutput(WIN, 3, "testword", "t e s t w o r d "),
                HangmanOutput(START, 0, "testword", "? ? ? ? ? ? ? ? "),
                HangmanOutput(FIRST_MISTAKE, 1, "testword", "? ? ? ? ? ? ? ? "),
                HangmanOutput(SECOND_MISTAKE, 2, "testword", "? ? ? ? ? ? ? ? "),
                HangmanOutput(THIRD_MISTAKE, 3, "testword", "? ? ? ? ? ? ? ? "),
                HangmanOutput(FOURTH_MISTAKE, 4, "testword", "? ? ? ? ? ? ? ? "),
                HangmanOutput(LOSE, 5, "testword", "t e s t w o r d ")
            })
        );
    }

    private static Stream<Arguments> gameWithWrongGuesses() {
        return Stream.of(
            Arguments.of(new TestDictionary(0), new TestPlayer(new String[]{"t", "E", "oops", "s", "T", "!", "¯\\_(ツ)_/¯", "w", "1", "o", "2", "r", "y", "d"}, 0), new String[] {
                getLengthErrorMessage("oops"),
                getRepeatedLetterErrorMessage("t"),
                getNotALetterErrorMessage("!"),
                getLengthErrorMessage("¯\\_(ツ)_/¯"),
                getNotALetterErrorMessage("1"),
                getNotALetterErrorMessage("2")
            }),
            Arguments.of(new TestDictionary(2), new TestPlayer(new String[]{"\\", "a\nb", "2 * x + 1 = 3", ""}, 0), new String[] {
                getNotALetterErrorMessage("\\"),
                "ERROR: you need to write just one letter, len('a",
                "b') = 3",
                getLengthErrorMessage("2 * x + 1 = 3"),
                getLengthErrorMessage("")
            })
        );
    }
}
