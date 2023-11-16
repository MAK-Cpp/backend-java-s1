package edu.hw5.tasks.task5;

import java.util.regex.Pattern;

public final class RussianLicensePlate {
    private static final Pattern PLATE_PATTERN =
        Pattern.compile("^[АВЕКМНОРСТУХ](00[1-9]|(0[1-9]|[1-9][0-9])[0-9])[АВЕКМНОРСТУХ]{2}[1-9]?\\d{2}$");

    private RussianLicensePlate() {
    }

    public static boolean check(final String plate) {
        return PLATE_PATTERN.matcher(plate).find();
    }
}
