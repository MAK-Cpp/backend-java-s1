package edu.hw3.tasks.task5;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;
import static edu.hw3.tasks.task5.ContactList.parseContacts;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ContactListTest {
    @ParameterizedTest
    @MethodSource("correctContacts")
    public void testCorrectContactList(final Collection<String> names, final String order, final List<Contact> sortedContacts) {
        assertThat(parseContacts(names, order)).isEqualTo(sortedContacts);
    }

    @ParameterizedTest
    @MethodSource("incorrectContacts")
    public void testIncorrectContactList(final Collection<String> names, final String order, final String errorMessage) {
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> parseContacts(names, order));
        assertThat(exception.getMessage()).isEqualTo(errorMessage);
    }

    private static Stream<Arguments> correctContacts() {
        return Stream.of(
            Arguments.of(
                List.of("John Locke", "Thomas Aquinas", "David Hume", "Rene Descartes"),
                "ASC",
                List.of(
                    new Contact("Thomas", "Aquinas"),
                    new Contact("Rene", "Descartes"),
                    new Contact("David", "Hume"),
                    new Contact("John", "Locke")
                )
            ),
            Arguments.of(
                List.of("Paul Erdos", "Leonhard Euler", "Carl Gauss"),
                "DESC",
                List.of(
                    new Contact("Carl", "Gauss"),
                    new Contact("Leonhard", "Euler"),
                    new Contact("Paul", "Erdos")
                )
            ),
            Arguments.of(
                List.of(),
                "DESC",
                List.of()
            ),
            Arguments.of(
                List.of("Maxim Primakov", "Evgenia Primakova", "Anton Primakov", "Roman Karandashov"),
                "ASC",
                List.of(
                    new Contact("Roman", "Karandashov"),
                    new Contact("Maxim", "Primakov"),
                    new Contact("Anton", "Primakov"),
                    new Contact("Evgenia", "Primakova")
                )
            )
        );
    }

    private static Stream<Arguments> incorrectContacts() {
        return Stream.of(
            Arguments.of(
                List.of("A B C", "Thomas Aquinas"),
                "ASC",
                "worng FIO format: 'A B C'"
            ),
            Arguments.of(
                List.of("Maksim Primakov"),
                "WHAT",
                "wrong order of sorting: WHAT"
            )
        );
    }
}
