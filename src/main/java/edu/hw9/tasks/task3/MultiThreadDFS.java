package edu.hw9.tasks.task3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.stream.Stream;

public class MultiThreadDFS {
    private final List<List<Integer>> graph;
    private final ArrayList<Integer> components;
    private int componentNumber;

    public MultiThreadDFS(List<List<Integer>> graph) {
        this.graph = graph;
        this.components = new ArrayList<>(Stream.generate(() -> -1).limit(graph.size()).toList());
        componentNumber = 0;
    }

    public List<Integer> findComponents() {
        if (componentNumber == 0) {
            try (ForkJoinPool forkJoinPool = ForkJoinPool.commonPool()) {
                for (int i = 0; i < components.size(); i++) {
                    if (components.get(i) == -1) {
                        forkJoinPool.invoke(new ComponentsSearcher(i, ++componentNumber));
                    }
                }
            }
        }
        return components;
    }

    public final class ComponentsSearcher extends RecursiveAction {
        private final int nodeId;
        private final int componentId;

        public ComponentsSearcher(int nodeId, int componentId) {
            this.nodeId = nodeId;
            this.componentId = componentId;
        }

        @Override
        protected void compute() {
            if (components.get(nodeId) != -1) {
                return;
            }
            components.set(nodeId, componentId);
            Stream<Integer> neighbours = graph.get(nodeId).stream();
            List<ComponentsSearcher> tasks = neighbours.map(i -> new ComponentsSearcher(i, componentId)).toList();
            ForkJoinTask.invokeAll(tasks);
        }
    }
}
