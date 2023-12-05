package edu.hw8.tasks.task2;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public final class FixedThreadPool implements ThreadPool {
    private final Thread[] threads;
    private final BlockingQueue<Runnable> taskQueue;
    private final BlockingQueue<Integer> freeThreads;

    private FixedThreadPool(Thread[] threads) {
        this.threads = threads;
        this.taskQueue = new LinkedBlockingQueue<>();
        this.freeThreads = new LinkedBlockingQueue<>(threads.length);
        for (int i = 0; i < threads.length; i++) {
            this.freeThreads.add(i);
        }
    }

    public static FixedThreadPool create(int n) {
        return new FixedThreadPool(new Thread[n]);
    }

    @Override
    public void start() {
        while (!taskQueue.isEmpty()) {
            try {
                Runnable task = taskQueue.take();
                int freeThread = freeThreads.take();
                this.threads[freeThread] = new Thread(() -> {
                    task.run();
                    freeThreads.add(freeThread);
                });
                this.threads[freeThread].start();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
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
                while (thread.isAlive()) {
                }
                thread.interrupt();
            }
        }
    }
}
