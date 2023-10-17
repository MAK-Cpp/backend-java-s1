package edu.project1.hangman;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;
import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOutNormalized;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HangmanTest {
    @ParameterizedTest
    @MethodSource("wrongDictionaries")
    void testHangmanWithWrongDictionary(final Dictionary wrongDictionary) {
        Hangman game = new Hangman(wrongDictionary, new TestPlayer(null, 0));
        HangmanException exception = assertThrows(HangmanException.class, game::play);
        assertThat(exception.getMessage()).isEqualTo("dictionary contains not allowed word(s)");
    }

    @ParameterizedTest
    @MethodSource("normalGame")
    void testHangmanNormalGame(final Dictionary dictionary, final Player player, final GameStatements statement, final int countMistakes, final String hiddenWord, final String hiddenWordForUser) throws Exception  {
        Hangman game = new Hangman(dictionary, player);
        String[] lines = tapSystemOutNormalized(game::play).split("\n");
        StringBuilder result = new StringBuilder(600);
        for (int i = 15 * (lines.length / 15 - 1); i < lines.length; i++) {
            result.append(lines[i]).append("\n");
        }
        assertThat(result.toString()).isEqualTo(Hangman.createOutput(statement, countMistakes, hiddenWord, hiddenWordForUser).toString());
    }

    private static Stream<Dictionary> wrongDictionaries() {
        return Stream.of(
            () -> "123",
            () -> " \s\n\t",
            () -> "hello, world!",
            () -> "Hi)",
            () -> "1sth4ty0u"
        );
    }

    private static Stream<Arguments> normalGame() {
        return Stream.of(
            Arguments.of(new TestDictionary(0), new TestPlayer(new String[]{"t", "E", "s", "T", "w", "o", "r", "y", "d"},0), GameStatements.WIN, 1, "testword", "t e s t w o r d "),
            Arguments.of(new TestDictionary(0), new TestPlayer(new String[]{"a", "b", "c", "f", "g"},0), GameStatements.LOSE, 5, "testword", "t e s t w o r d "),
            Arguments.of(new TestDictionary(0), new TestPlayer(new String[]{"T", "x", "E", "coNceDE"},0), GameStatements.CONCEDE, 1, "testword", "t e s t w o r d "),
            Arguments.of(new TestDictionary(1), new TestPlayer(new String[]{"A", "B", "C", "D", "E", "F", "R"}, 0), GameStatements.WIN, 2, "abracadabra", "a b r a c a d a b r a "),
            Arguments.of(new TestDictionary(1), new TestPlayer(new String[]{}, 0), GameStatements.CONCEDE, 0, "abracadabra", "a b r a c a d a b r a "),
            Arguments.of(new TestDictionary(2), new TestPlayer(new String[]{"h", "s", "e", "m", "i", "p", "t", "o"}, 0), GameStatements.WIN, 4, "itmo", "i t m o "),
            Arguments.of(new TestDictionary(2), new TestPlayer(new String[]{"o", "A", "o", "O", "o", "O", "o", "O", "i", "t", "M", "O"}, 0), GameStatements.WIN, 1, "itmo", "i t m o ")

        );
    }

    private static Stream<Arguments> repetativeGame() {
        return Stream.of(

        );
    }
}
