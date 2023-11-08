package edu.hw4.tasks.task17;

public final class Counter {
    private int countDogs;
    private int countBitesDogs;
    private int countSpiders;
    private int countBitesSpiders;

    private Counter() {
    }

    public static Counter of() {
        return new Counter();
    }

    public Counter add(final Counter other) {
        this.countDogs += other.countDogs;
        this.countBitesDogs += other.countBitesDogs;
        this.countSpiders += other.countSpiders;
        this.countBitesSpiders += other.countBitesSpiders;
        return this;
    }

    public Counter addDog(boolean isBite) {
        this.countDogs++;
        if (isBite) {
            this.countBitesDogs++;
        }
        return this;
    }

    public Counter addSpider(boolean isBite) {
        this.countSpiders++;
        if (isBite) {
            this.countBitesSpiders++;
        }
        return this;
    }

    public boolean isCorrect() {
        return countBitesSpiders * countDogs > countBitesDogs * countSpiders;
    }
}
