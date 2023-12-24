package edu.hw10.tasks.task1;

public class Car {
    private final String carName;
    private final int countWheels;
    private final int countDoors;
    private final boolean isLeftHanded;
    private final Person owner;

    @Max(value = 4)
    @Min(value = 2)
    @NotNull
    public Car(String carName, int countWheels, int countDoors, boolean isLeftHanded, Person owner) {
        this.carName = carName;
        this.countWheels = countWheels;
        this.countDoors = countDoors;
        this.isLeftHanded = isLeftHanded;
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "MyClass{" +
            "carName='" + carName + '\'' +
            ", countWheels=" + countWheels +
            ", countDoors=" + countDoors +
            ", isLeftHanded=" + isLeftHanded +
            ", owner=" + owner +
            '}';
    }

    public static Car newBMW(int countDoors, boolean isLeftHanded, Person owner) {
        return new Car("BMW", 4, countDoors, isLeftHanded, owner);
    }
}
