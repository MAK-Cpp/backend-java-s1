package edu.hw2.tasks.task3;

import java.util.Random;

public class DefaultConnectionManager implements ConnectionManager {
    private final double failConnectionProbability;
    private final double failProbability;

    public DefaultConnectionManager() {
        this(new Random().nextDouble(), new Random().nextDouble());
    }

    /*package-private*/ DefaultConnectionManager(double failConnectionProbability, double failProbability) {
        this.failConnectionProbability = failConnectionProbability;
        this.failProbability = failProbability;
    }

    /*package-private*/ double getFailConnectionProbability() {
        return failConnectionProbability;
    }

    @Override
    public Connection getConnection() {
        return (new Random().nextDouble() > failConnectionProbability)
            ? new StableConnection() : new FaultyConnection(failProbability);
    }
}
