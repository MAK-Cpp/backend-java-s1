package edu.hw1.tasks;

public final class Task4 {
    private Task4() {
    }

    public static String fixString(final String brokenString) {
        StringBuilder builderFixedString = new StringBuilder();
        for (int i = 0; i < brokenString.length(); i += 2) {
            if (i == brokenString.length() - 1) {
                builderFixedString.append(brokenString.charAt(i));
                break;
            }
            builderFixedString.append(brokenString.charAt(i + 1));
            builderFixedString.append(brokenString.charAt(i));
        }
        return builderFixedString.toString();
    }
}
