package edu.hw8.tasks.task2;

import java.time.Duration;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Stream;

public final class FixedThreadPool implements ThreadPool {
    private final Thread[] threads;
    private final BlockingQueue<Runnable> taskQueue;
    private static final int MILLIS_TO_STOP = 1000;

    private FixedThreadPool(int n) {
        this.taskQueue = new LinkedBlockingQueue<>();
        this.threads = Stream.generate(() -> new Thread(() -> {
            while (!taskQueue.isEmpty()) {
                try {
                    Runnable task = this.taskQueue.take();
                    task.run();
                } catch (InterruptedException ignored) {
                }
            }
        })).limit(n).toArray(Thread[]::new);
    }

    public static FixedThreadPool create(int n) {
        return new FixedThreadPool(n);
    }

    @Override
    public void start() {
        for (Thread thread : threads) {
            thread.start();
        }
    }

    @Override
    public void execute(Runnable runnable) {
        taskQueue.add(runnable);
    }

    @Override
    public void close() {
        for (Thread thread : threads) {
            if (thread != null) {
                try {
                    boolean isTerminated = thread.join(Duration.ofMillis(MILLIS_TO_STOP));
                    if (!isTerminated) {
                        thread.interrupt();
                    }
                } catch (InterruptedException ignored) {
                }
            }
        }
    }
}
