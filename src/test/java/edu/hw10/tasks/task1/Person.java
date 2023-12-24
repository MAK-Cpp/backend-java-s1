package edu.hw10.tasks.task1;

public record Person(String name, int age, boolean male) {
    @NotNull
    @Max(value = 13)
    public static Person born(String name) {
        return new Person(name, 0, Math.random() > 0.5);
    }
}
