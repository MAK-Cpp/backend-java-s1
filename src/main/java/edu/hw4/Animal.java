package edu.hw4;

public record Animal(
    String name,
    Type type,
    Sex sex,
    int age,
    int height,
    int weight,
    Boolean bites
) {
    public static final int COUNT_MAMMALS_PAWS = 4;
    public static final int COUNT_BIRDS_PAWS = 2;
    public static final int COUNT_FISH_PAWS = 0;
    public static final int COUNT_INSECTS_PAWS = 8;

    public enum Type {
        CAT, DOG, BIRD, FISH, SPIDER
    }

    public enum Sex {
        M, F
    }

    public int paws() {
        return switch (type) {
            case CAT, DOG -> COUNT_MAMMALS_PAWS;
            case BIRD -> COUNT_BIRDS_PAWS;
            case FISH -> COUNT_FISH_PAWS;
            case SPIDER -> COUNT_INSECTS_PAWS;
        };
    }
}
