package edu.hw7.tasks.task4;

import java.security.SecureRandom;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public final class MonteCarloMethod {
    private static AtomicInteger circleCount = new AtomicInteger(0);
    private static AtomicInteger totalCount = new AtomicInteger(0);
    private static final SecureRandom RANDOM = new SecureRandom();

    private MonteCarloMethod() {
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    public static double pi(int n) {
        for (int i = 0; i < n; i++) {
            double x = RANDOM.nextDouble(0, 2);
            double y = RANDOM.nextDouble(0, 2);
            if (Math.sqrt((x - 1) * (x - 1) + (y - 1) * (y - 1)) <= 1) {
                circleCount.incrementAndGet();
            }
            totalCount.incrementAndGet();
        }
        return 4 * ((double) circleCount.get() / totalCount.get());
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    public static double pi(int n, int cores) {
        try (ExecutorService threadPool = Executors.newFixedThreadPool(cores)) {
            CompletableFuture[] tasks = Stream.generate(() -> CompletableFuture.runAsync(() -> {
                for (int i = 0; i < (n + cores - 1) / cores; i++) {
                    ThreadLocalRandom random = ThreadLocalRandom.current();
                    double x = random.nextDouble(0, 2);
                    double y = random.nextDouble(0, 2);
                    if (Math.sqrt((x - 1) * (x - 1) + (y - 1) * (y - 1)) <= 1) {
                        circleCount.incrementAndGet();
                    }
                    totalCount.incrementAndGet();
                }
            }, threadPool)).limit(cores).toArray(CompletableFuture[]::new);
            CompletableFuture.allOf(tasks).join();
            threadPool.shutdown();
            return 4 * ((double) circleCount.get() / totalCount.get());
        }
    }
}
