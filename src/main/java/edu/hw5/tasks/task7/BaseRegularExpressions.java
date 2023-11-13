package edu.hw5.tasks.task7;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum BaseRegularExpressions {
    FIRST_PATTERN("^[01]{2}0[01]*$"),
    SECOND_PATTERN("^((0[01]*0)|(1[01]*1)|[01])$"),
    THIRD_PATTERN("^[01]{1,3}$");

    private final Pattern pattern;

    BaseRegularExpressions(final String regex) {
        pattern = Pattern.compile(regex);
    }

    public boolean find(final CharSequence input) {
        return pattern.matcher(input).find();
    }
}
