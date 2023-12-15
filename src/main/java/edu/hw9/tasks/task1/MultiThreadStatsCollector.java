package edu.hw9.tasks.task1;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

public class MultiThreadStatsCollector implements StatsCollector {
    private final ConcurrentHashMap<String, Statistic> stats = new ConcurrentHashMap<>();

    public MultiThreadStatsCollector() {
    }

    @Override
    public void push(String statName, double... values) {
        try (ExecutorService virtualThreads = Executors.newVirtualThreadPerTaskExecutor()) {
            Statistic toMerge = new Statistic(statName);
            var futures = Stream.of(
                CompletableFuture.runAsync(() -> toMerge.addToSum(values), virtualThreads),
                CompletableFuture.runAsync(() -> toMerge.addToAverage(values), virtualThreads),
                CompletableFuture.runAsync(() -> toMerge.updateMax(values), virtualThreads),
                CompletableFuture.runAsync(() -> toMerge.updateMin(values), virtualThreads)
            ).toArray(CompletableFuture[]::new);
            CompletableFuture.allOf(futures).join();
            stats.merge(statName, toMerge, Statistic::merge);
        }
    }

    @Override
    public List<Statistic> stats() {
        return stats.values().stream().toList();
    }
}
