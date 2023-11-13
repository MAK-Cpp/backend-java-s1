package edu.hw5.tasks.task5;

import java.util.regex.Pattern;

public final class RussianLicensePlate {
    private static final Pattern PLATE_PATTERN = Pattern.compile("^[А-Я]\\d{3}[А-Я]{2}\\d{3}$");

    private RussianLicensePlate() {
    }

    public static boolean check(final String plate) {
        return PLATE_PATTERN.matcher(plate).find();
    }
}
