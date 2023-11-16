package edu.hw5.tasks.task6;

import java.util.regex.Pattern;

public final class SubstringInString {
    private SubstringInString() {
    }

    public static boolean check(final String string, final String text) {
        final Pattern substringPattern = Pattern.compile(Pattern.quote(string));
        return substringPattern.matcher(text).find();
    }
}
