package edu.hw4.tasks.task17;

public final class Counter {
    private int answer;
    private int countDogs;
    private int countSpiders;

    private Counter(int answer, int countDogs, int countSpiders) {
        this.answer = answer;
        this.countDogs = countDogs;
        this.countSpiders = countSpiders;
    }

    public static Counter of(int answer, int countDogs, int countSpiders) {
        return new Counter(answer, countDogs, countSpiders);
    }

    public Counter add(final Counter other) {
        this.answer += other.answer;
        this.countDogs += other.countDogs;
        this.countSpiders += other.countSpiders;
        return this;
    }

    public Counter addDog() {
        this.answer--;
        this.countDogs++;
        return this;
    }

    public Counter addSpider() {
        this.answer++;
        this.countSpiders++;
        return this;
    }

    public boolean isCorrect() {
        return answer > 0 && countSpiders > 0 && countDogs > 0;
    }
}
