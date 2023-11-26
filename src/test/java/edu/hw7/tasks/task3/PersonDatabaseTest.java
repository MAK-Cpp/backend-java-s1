package edu.hw7.tasks.task3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Task 3")
class PersonDatabaseTest {
    public sealed interface Command {
    }

    private record Add(Person person) implements Command {
    }

    private record Delete(int id) implements Command {
    }

    private record FindByName(String name) implements Command {
    }

    private record FindByAddress(String address) implements Command {
    }

    private record FindByPhone(String phone) implements Command {
    }

    private record EmptyCommand() implements Command {
    }

    public static Stream<List<List<Command>>> DatabaseRequests() {
        return Stream.of(
            List.of(
                List.of(new Add(new Person(1, "Maxim", "Vyazemski pereulok 5/7", "+79511124785"))),
                List.of(new FindByName("Maxim"), new FindByAddress("Vyazemski pereulok 5/7")),
                List.of(new Delete(1)),
                List.of(new FindByName("Maxim"), new FindByAddress("Vyazemski pereulok 5/7"))
            )
        );
    }

    private void testPersonDatabase(PersonDatabase database, List<List<Command>> commandsList) {
        for (List<Command> commands : commandsList) {
            List<Person> result = List.of();
            for (Command input : commands) {
                switch (input) {
                    case Add(Person person) -> database.add(person);
                    case Delete(int id) -> database.delete(id);
                    case FindByName(String name) -> {
                        if (result.isEmpty()) {
                            result = database.findByName(name);
                        } else {
                            assertThat(database.findByName(name)).isEqualTo(result);
                        }
                    }
                    case FindByAddress(String address) -> {
                        if (result.isEmpty()) {
                            result = database.findByAddress(address);
                        } else {
                            assertThat(database.findByAddress(address)).isEqualTo(result);
                        }
                    }
                    case FindByPhone(String phone) -> {
                        if (result.isEmpty()) {
                            result = database.findByPhone(phone);
                        } else {
                            assertThat(database.findByPhone(phone)).isEqualTo(result);
                        }
                    }
                    case EmptyCommand() -> {
                    }
                }
            }
        }
    }

    @ParameterizedTest
    @MethodSource("DatabaseRequests")
    void testReadWritePersonDatabase(List<List<Command>> commands) {
        testPersonDatabase(new ReadWritePersonDatabase(), commands);
    }

    @ParameterizedTest
    @MethodSource("DatabaseRequests")
    void testSynchronizedPersonDatabase(List<List<Command>> commands) {
        testPersonDatabase(new SynchronizedPersonDatabase(), commands);
    }
}
