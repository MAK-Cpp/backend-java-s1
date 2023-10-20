package edu.hw2.tasks.task3;

import java.util.Random;

public class FaultyConnection implements Connection {
    private final double failProbability;

    public FaultyConnection() {
        this(new Random().nextDouble());
    }

    /*package-private*/ FaultyConnection(double failProbability) {
        this.failProbability = failProbability;
    }

    /*package-private*/ double getFailProbability() {
        return failProbability;
    }

    @Override
    public void execute(String command) throws ConnectionException {
        if (new Random().nextDouble() <= failProbability) {
            throw new ConnectionException("connection was aborted");
        }
    }

    @Override
    public void close() throws Exception {
    }
}
