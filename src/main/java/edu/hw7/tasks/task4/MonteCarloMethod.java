package edu.hw7.tasks.task4;

import java.security.SecureRandom;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Stream;

public final class MonteCarloMethod {
    private MonteCarloMethod() {
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    public static double pi(int n) {
        final LongAdder circleCount = new LongAdder();
        final LongAdder totalCount = new LongAdder();
        final SecureRandom RANDOM = new SecureRandom();
        for (int i = 0; i < n; i++) {
            double x = RANDOM.nextDouble(0, 2);
            double y = RANDOM.nextDouble(0, 2);
            if (Math.sqrt((x - 1) * (x - 1) + (y - 1) * (y - 1)) <= 1) {
                circleCount.increment();
            }
            totalCount.increment();
        }
        return 4 * (circleCount.doubleValue() / totalCount.doubleValue());
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    private static double pi(int n, int cores) {
        final LongAdder circleCount = new LongAdder();
        final LongAdder totalCount = new LongAdder();
        try (ExecutorService threadPool = Executors.newFixedThreadPool(cores)) {
            CompletableFuture[] tasks = Stream.generate(() -> CompletableFuture.runAsync(() -> {
                for (int i = 0; i < (n + cores - 1) / cores; i++) {
                    ThreadLocalRandom random = ThreadLocalRandom.current();
                    double x = random.nextDouble(0, 2);
                    double y = random.nextDouble(0, 2);
                    if (Math.sqrt((x - 1) * (x - 1) + (y - 1) * (y - 1)) <= 1) {
                        circleCount.increment();
                    }
                    totalCount.increment();
                }
            }, threadPool)).limit(cores).toArray(CompletableFuture[]::new);
            CompletableFuture.allOf(tasks).join();
        }
        return 4 * (circleCount.doubleValue() / totalCount.doubleValue());
    }

    public static double fastPi(int n) {
        return pi(n, Runtime.getRuntime().availableProcessors());
    }
}
