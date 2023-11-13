package edu.hw5.tasks.task4;

import java.util.regex.Pattern;

public final class PasswordSecurity {
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(".*[~!@#$%^&*|].*");

    private PasswordSecurity() {
    }

    public static boolean check(final String password) {
        return PASSWORD_PATTERN.matcher(password).find();
    }
}
