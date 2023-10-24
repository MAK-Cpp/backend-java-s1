package edu.hw2.tasks.task3;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FaultyConnectionTest {
    @Test
    void testFaultyConnectionRandom() throws Exception {
        try(FaultyConnection connection = new FaultyConnection()) {
            int countExceptions = 0;
            final int countAttempts = 1_000_00;
            for (int attempt = 0; attempt < countAttempts; attempt++) {
                try {
                    connection.execute("");
                } catch (ConnectionException e) {
                    countExceptions++;
                }
            }
            assertEquals(connection.getFailProbability(), (double) countExceptions / countAttempts, 1e-2);
        }
    }
}
