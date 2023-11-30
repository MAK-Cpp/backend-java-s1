package edu.hw7.tasks.task1;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public final class ThreadIncrements {
    private static AtomicInteger counter = new AtomicInteger(0);
    private static final int THREAD_COUNTS = 10;

    private ThreadIncrements() {
    }

    private static void func(final int count, final boolean isIncrement) {
        for (int i = 0; i < count; i++) {
            if (isIncrement) {
                counter.incrementAndGet();
            } else {
                counter.decrementAndGet();
            }
        }
    }

    public static void resetCounter() {
        counter.set(0);
    }

    public static void run(final int count, final boolean isIncrement) {
        try (ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_COUNTS)) {
            var tasks = Stream.generate(() -> CompletableFuture.runAsync(() -> func(count, isIncrement), threadPool))
                .limit(THREAD_COUNTS).toArray(CompletableFuture[]::new);
            CompletableFuture.allOf(tasks).join();
        }
    }

    public static int getCounter() {
        return counter.get();
    }
}
