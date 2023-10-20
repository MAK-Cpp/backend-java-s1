package edu.hw2.tasks.task3;

import java.util.Random;

public class FaultyConnectionManager implements ConnectionManager {
    private final double failConnectionProbability;

    public FaultyConnectionManager() {
        this(new Random().nextDouble());
    }

    /*package-private*/ FaultyConnectionManager(double failConnectionProbability) {
        this.failConnectionProbability = failConnectionProbability;
    }

    @Override
    public Connection getConnection() {
        return new FaultyConnection(failConnectionProbability);
    }
}
