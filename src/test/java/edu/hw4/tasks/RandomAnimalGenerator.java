package edu.hw4.tasks;

import edu.hw4.Animal;
import java.util.Random;

public final class RandomAnimalGenerator {
    private static final Random RANDOM = new Random();

    private RandomAnimalGenerator() {
    }

    public static String randomName() {
        final int nameLength = RANDOM.nextInt(1, 50);
        StringBuilder nameBuilder = new StringBuilder(nameLength);
        for (int i = 0; i < nameLength; i++) {
            nameBuilder.append((char) (RANDOM.nextInt('z' - 'a') + 'a'));
        }
        return nameBuilder.toString();
    }

    public static Animal.Type randomType() {
        final Animal.Type[] values = Animal.Type.values();
        return values[RANDOM.nextInt(values.length)];
    }

    public static Animal.Sex randomSex() {
        final Animal.Sex[] values = Animal.Sex.values();
        return values[RANDOM.nextInt(values.length)];
    }

    public static int randomAge() {
        return RANDOM.nextInt(1000);
    }

    public static int randomHeight() {
        return RANDOM.nextInt(1000);
    }

    public static int randomWeight() {
        return RANDOM.nextInt(1000);
    }

    public static boolean randomBite() {
        return RANDOM.nextBoolean();
    }

    public static Animal randomAnimal(
        final String name,
        final Animal.Type type,
        final Animal.Sex sex,
        final Integer age,
        final Integer height,
        final Integer weight,
        final Boolean bites
    ) {
        return new Animal(
            (name == null ? randomName() : name),
            (type == null ? randomType() : type),
            (sex == null ? randomSex() : sex),
            (age == null ? randomAge() : age),
            (height == null ? randomHeight() : height),
            (weight == null ? randomWeight() : weight),
            (bites == null ? randomBite() : bites)
        );
    }
}
