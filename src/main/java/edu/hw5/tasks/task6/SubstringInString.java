package edu.hw5.tasks.task6;

import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class SubstringInString {
    private static final Map<Character, String> SPECIAL_CHARACTERS = Map.of(
        '\n', "\\n",
        '\t', "\\t",
        '\r', "\\r",
        '\f', "\\f",
        '\s', "\\s",
        '\b', "\\b"
    );
    private static final String REGEX_CHARACTERS = "<([{\\^-=$!|]})?*+.>";

    private SubstringInString() {
    }

    public static boolean check(final String string, final String text) {
        final Pattern substringPattern = Pattern.compile(string.chars()
            .mapToObj(x -> {
                Character charX = (char) x;
                if (SPECIAL_CHARACTERS.containsKey(charX)) {
                    return SPECIAL_CHARACTERS.get(charX);
                } else if (REGEX_CHARACTERS.contains(charX.toString())) {
                    return "\\" + charX;
                } else {
                    return charX.toString();
                }
            })
            .collect(Collectors.joining()));
        return substringPattern.matcher(text).find();
    }
}
