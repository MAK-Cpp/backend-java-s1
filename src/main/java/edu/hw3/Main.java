package edu.hw3;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Main {
    private static final Pattern FIO_PATTERN = Pattern.compile("^ *([a-zA-Z]+) *([a-zA-Z]+)? *$");


    private Main() {
    }

    public static void main(String[] args) {
        Matcher matcher = FIO_PATTERN.matcher("a b c");

    }
}
