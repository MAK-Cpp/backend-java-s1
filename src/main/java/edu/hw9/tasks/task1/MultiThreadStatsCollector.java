package edu.hw9.tasks.task1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class MultiThreadStatsCollector implements StatsCollector {
    private final ConcurrentHashMap<String, Double> sums;
    private final ConcurrentHashMap<String, AverageValue> averages;
    private final ConcurrentHashMap<String, Double> minimums;
    private final ConcurrentHashMap<String, Double> maximums;

    public MultiThreadStatsCollector() {
        this.sums = new ConcurrentHashMap<>();
        this.averages = new ConcurrentHashMap<>();
        this.minimums = new ConcurrentHashMap<>();
        this.maximums = new ConcurrentHashMap<>();
    }

    @Override
    public void push(String statName, double... values) {
        try (ExecutorService virtualThreads = Executors.newVirtualThreadPerTaskExecutor()) {
            var futures = Stream.of(
                CompletableFuture.runAsync(() -> {
                    for (double value : values) {
                        sums.merge(statName, value, Double::sum);
                    }
                }, virtualThreads),
                CompletableFuture.runAsync(
                    () -> averages.merge(statName, new AverageValue(values), AverageValue::add),
                    virtualThreads
                ),
                CompletableFuture.runAsync(() -> {
                    for (double value : values) {
                        maximums.merge(statName, value, Double::max);
                    }
                }, virtualThreads),
                CompletableFuture.runAsync(() -> {
                    for (double value : values) {
                        minimums.merge(statName, value, Double::min);
                    }
                }, virtualThreads)
            ).toArray(CompletableFuture[]::new);
            CompletableFuture.allOf(futures).join();
        }
    }

    @Override
    public List<Statistic> stats() {
        ArrayList<Statistic> ans = new ArrayList<>(sums.size());
        for (String name : sums.keySet()) {
            ans.add(new Statistic(
                name,
                sums.get(name),
                averages.get(name).value(),
                maximums.get(name),
                minimums.get(name)
            ));
        }
        return ans;
    }
}
