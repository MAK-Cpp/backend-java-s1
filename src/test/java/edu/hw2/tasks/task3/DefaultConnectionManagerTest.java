package edu.hw2.tasks.task3;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DefaultConnectionManagerTest {
    @Test
    void testDefaultConnectionManagerRandom() {
        int countFaultyConnections = 0;
        final int countAttempts = 1_000_00;
        DefaultConnectionManager manager = new DefaultConnectionManager();
        for (int attempt = 0; attempt < countAttempts; attempt++) {
            countFaultyConnections += (manager.getConnection() instanceof FaultyConnection) ? 1 : 0;
        }
        assertEquals(manager.getFailConnectionProbability(), (double) countFaultyConnections / countAttempts, 1e-2);
    }
}
