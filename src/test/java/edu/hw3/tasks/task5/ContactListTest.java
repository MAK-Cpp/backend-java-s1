package edu.hw3.tasks.task5;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static edu.hw3.tasks.task5.ContactList.parseContacts;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ContactListTest {
    @ParameterizedTest
    @MethodSource("correctContacts")
    public void testCorrectContactList(final String[] names, final String order, final Object[] sortedNames) {
        assertThat(parseContacts(names, order)).isEqualTo(sortedNames);
    }

    @ParameterizedTest
    @MethodSource("incorrectContacts")
    public void testIncorrectContactList(final String[] names, final String order, final String errorMessage) {
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> parseContacts(names, order));
        assertThat(exception.getMessage()).isEqualTo(errorMessage);
    }

    private static Stream<Arguments> correctContacts() {
        return Stream.of(
            Arguments.of(
                new String[] {"John Locke", "Thomas Aquinas", "David Hume", "Rene Descartes"},
                "ASC",
                new Object[] {"Thomas Aquinas", "Rene Descartes", "David Hume", "John Locke"}
            ),
            Arguments.of(
                new String[] {"Paul Erdos", "Leonhard Euler", "Carl Gauss"},
                "DESC",
                new Object[] {"Carl Gauss", "Leonhard Euler", "Paul Erdos"}
            ),
            Arguments.of(
                new String[0],
                "DESC",
                new Object[0]
            ),
            Arguments.of(
                null,
                "DESC",
                new Object[0]
            ),
            Arguments.of(
                new String[] {"Maxim Primakov", "Evgenia Primakova", "Anton Primakov", "Roman Karandashov"},
                "ASC",
                new Object[] {"Roman Karandashov", "Maxim Primakov", "Anton Primakov", "Evgenia Primakova"}
            )
        );
    }

    private static Stream<Arguments> incorrectContacts() {
        return Stream.of(
            Arguments.of(
                new String[] {"A B C", "Thomas Aquinas"},
                "ASC",
                "wrong name format"
            ),
            Arguments.of(
                new String[] {"Maksim Primakov"},
                "WHAT",
                "wrong order of sorting: WHAT"
            )
        );
    }
}
