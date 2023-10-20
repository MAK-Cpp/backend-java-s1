package edu.hw2.tasks.task3;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PopularCommandExecutorTest {
    @ParameterizedTest
    @MethodSource("connectionsWithoutErrors")
    void testConnectionsWithoutErrors(final ConnectionManager manager, final int maxAttempts) {
        PopularCommandExecutor executor = new PopularCommandExecutor(manager, maxAttempts);
        assertDoesNotThrow(executor::updatePackages);
    }

    @ParameterizedTest
    @MethodSource("connectionWithErrors")
    void testConnectionWithErrors(final ConnectionManager manager, final int maxAttempts) {
        PopularCommandExecutor executor = new PopularCommandExecutor(manager, maxAttempts);
        ConnectionException exception = assertThrows(ConnectionException.class, executor::updatePackages);
        assertEquals("exceeded the number of attempts", exception.getMessage());
        assertEquals("connection was aborted", exception.getCause().getMessage());
    }

    @ParameterizedTest
    @MethodSource("connectionManagers")
    void testConnectionWithProbablyErrors(final ConnectionManager manager, final int maxAttempts) {
        PopularCommandExecutor executor = new PopularCommandExecutor(manager, maxAttempts);
        try {
            executor.updatePackages();
        } catch (ConnectionException exception) {
            assertEquals("exceeded the number of attempts", exception.getMessage());
            assertEquals("connection was aborted", exception.getCause().getMessage());
        }
    }

    private static Stream<Arguments> connectionsWithoutErrors() {
        return Stream.of(
            Arguments.of(new DefaultConnectionManager(0.00, 0.00), 10_000),
            Arguments.of(new DefaultConnectionManager(0.25, 0.00), 10_000),
            Arguments.of(new DefaultConnectionManager(0.50, 0.00), 10_000),
            Arguments.of(new DefaultConnectionManager(0.75, 0.00), 10_000),
            Arguments.of(new DefaultConnectionManager(1.00, 0.00), 10_000),
            Arguments.of(new DefaultConnectionManager(0.00, 0.25), 10_000),
            Arguments.of(new DefaultConnectionManager(0.00, 0.50), 10_000),
            Arguments.of(new DefaultConnectionManager(0.00, 0.75), 10_000),
            Arguments.of(new DefaultConnectionManager(0.00, 1.00), 10_000),
            Arguments.of(new FaultyConnectionManager(0.00), 10_000)
        );
    }

    private static Stream<Arguments> connectionWithErrors() {
        return Stream.of(
            Arguments.of(new DefaultConnectionManager(1.00, 1.00), 10_000),
            Arguments.of(new FaultyConnectionManager(1.00), 10_000)
        );
    }

    private static Stream<Arguments> connectionManagers() {
        return Stream.of(
            Arguments.of(new DefaultConnectionManager(), 10_000),
            Arguments.of(new FaultyConnectionManager(), 10_000)
        );
    }
}
