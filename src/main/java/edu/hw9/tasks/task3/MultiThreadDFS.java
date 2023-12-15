package edu.hw9.tasks.task3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public final class MultiThreadDFS {

    private MultiThreadDFS() {
    }

    public static List<Integer> findWay(List<List<Integer>> graph, int start, int finish) {
        boolean[] wereHere = new boolean[graph.size()];
        try (ForkJoinPool forkJoinPool = ForkJoinPool.commonPool()) {
            return forkJoinPool.invoke(new ComponentsSearcher(graph, wereHere, start, finish));
        }
    }

    public static final class ComponentsSearcher extends RecursiveTask<ArrayList<Integer>> {
        private final List<List<Integer>> graph;
        private final boolean[] wereHere;
        private final int current;
        private final int finish;

        public ComponentsSearcher(List<List<Integer>> graph, boolean[] wereHere, int current, int finish) {
            this.graph = graph;
            this.wereHere = wereHere;
            this.current = current;
            this.finish = finish;
        }

        private ComponentsSearcher subtask(int newCurrent, boolean[] newWereHere) {
            return new ComponentsSearcher(graph, newWereHere, newCurrent, finish);
        }

        @Override
        protected ArrayList<Integer> compute() {
            if (current == finish) {
                return new ArrayList<>(List.of(current));
            } else if (wereHere[current]) {
                return null;
            }
            List<Integer> neighbours = graph.get(current);
            boolean[] newWereHere = new boolean[wereHere.length];
            System.arraycopy(wereHere, 0, newWereHere, 0, wereHere.length);
            newWereHere[current] = true;
            var invokedResults = ForkJoinTask.invokeAll(neighbours.stream().map(x -> subtask(x, newWereHere)).toList());
            var results = invokedResults.stream().map(ForkJoinTask::join);
            Optional<ArrayList<Integer>> optionalResult =
                results.filter(Objects::nonNull).min(Comparator.comparingInt(List::size));
            if (optionalResult.isEmpty()) {
                return null;
            }
            ArrayList<Integer> result = optionalResult.get();
            result.addFirst(current);
            return result;
        }
    }
}
