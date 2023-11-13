package edu.hw4;

public record Animal(
    String name,
    Type type,
    Sex sex,
    int age,
    int height,
    int weight,
    boolean bites
) {
    public static final int COUNT_MAMMALS_PAWS = 4;
    public static final int COUNT_BIRDS_PAWS = 2;
    public static final int COUNT_FISH_PAWS = 0;
    public static final int COUNT_INSECTS_PAWS = 8;

    public enum Type {
        CAT(35, 55, 60),
        DOG(40, 90, 80),
        BIRD(20, 220, 100),
        FISH(10, 100, 30),
        SPIDER(5, 10, 3);

        private final int maximumAge;
        private final int maximumHeight;
        private final int maximumWeight;

        Type(int maximumAge, int maximumHeight, int maximumWeight) {
            this.maximumAge = maximumAge;
            this.maximumHeight = maximumHeight;
            this.maximumWeight = maximumWeight;
        }

        public int getMaximumAge() {
            return maximumAge;
        }

        public int getMaximumHeight() {
            return maximumHeight;
        }

        public int getMaximumWeight() {
            return maximumWeight;
        }
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
