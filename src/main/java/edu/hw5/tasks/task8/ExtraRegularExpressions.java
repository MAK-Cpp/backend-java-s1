package edu.hw5.tasks.task8;

import java.util.regex.Pattern;

public enum ExtraRegularExpressions {
    FIRST_PATTERN("^([01][01])*[01]$"),
    SECOND_PATTERN("^((1[01])|0)([01][01])*$"),
    THIRD_PATTERN("^(1|(01*01*0))*$"),
    FOURTH_PATTERN("^(^1$)|(^((1((1((1[01])|0))|0))|0)[01]*$)$"),
    FIFTH_PATTERN("^(1[01])*(1)?$"),
    SIXTH_PATTERN("^0{2,}(10*)?$|^(01|10)0+$"),
    SEVENTH_PATTERN("^(0|10)*(1)?$");

    private final Pattern pattern;

    ExtraRegularExpressions(final String regex) {
        pattern = Pattern.compile(regex);
    }

    public boolean find(final CharSequence input) {
        return pattern.matcher(input).find();
    }
}
