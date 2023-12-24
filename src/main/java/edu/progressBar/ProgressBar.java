package edu.progressBar;

import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings({"checkstyle:MagicNumber", "checkstyle:RegexpSinglelineJava"})
public class ProgressBar implements Runnable {
    private char rollingChar;
    private final int length;
    private final AtomicInteger counter;
    private final int maximum;
    private final String format;

    public ProgressBar(int length, AtomicInteger counter, int maximum) {
        this.rollingChar = '\\';
        this.length = length;
        this.counter = counter;
        this.maximum = maximum;
        this.format = " %c [%-" + length + "s] %3d%%";
    }

    private void print(int percent) {
        System.out.printf(format, rollingChar, "=".repeat(percent * length / 100), percent);
        System.out.print(percent < 100 ? "\r" : "\n");
        rollingChar = switch (rollingChar) {
            case '\\' -> '|';
            case '|' -> '/';
            case '/' -> '—';
            case '—' -> '\\';
            case '×' -> '+';
            case '+' -> '×';
            default -> throw new RuntimeException();
        };
    }

    private void print(double part) {
        print((int) (part * 100));
    }

    private void print(int count, int of) {
        print((int) (((double) count) / of * 100));
    }

    private void printFinish() {
        System.out.printf(format, rollingChar, "=".repeat(length), 100);
        System.out.println();
    }

    @Override
    public void run() {
        while (counter.get() < maximum) {
            try {
                print(counter.get(), maximum);
                Thread.sleep(300);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        }
        printFinish();
    }
}
