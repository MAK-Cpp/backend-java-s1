package edu.hw8.tasks.task2;

import java.util.concurrent.atomic.AtomicInteger;

public final class FixedThreadPool implements ThreadPool {
    private final Thread[] threads;
    private final AtomicInteger countFreeThreads;

    private FixedThreadPool(Thread[] threads) {
        this.threads = threads;
        this.countFreeThreads = new AtomicInteger(threads.length);
    }

    public static FixedThreadPool create(int n) {
        return new FixedThreadPool(new Thread[n]);
    }

    @Override
    public void start() {
        for (Thread thread : threads) {
            if (thread != null) {
                thread.start();
            }
        }
    }

    @Override
    public void execute(Runnable runnable) {
        while (countFreeThreads.get() == 0) {
        }
        for (int i = 0; i < threads.length; i++) {
            if (threads[i] == null || !threads[i].isAlive()) {
                threads[i] = new Thread(() -> {
                    countFreeThreads.decrementAndGet();
                    runnable.run();
                    countFreeThreads.incrementAndGet();
                });
            }
        }
    }

    @Override
    public void close() throws Exception {
        for (Thread thread : threads) {
            if (thread != null) {
                thread.interrupt();
            }
        }
    }
}
