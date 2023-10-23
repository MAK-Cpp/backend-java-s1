package edu.hw3.tasks.task1;

public final class AtbashCipher {
    private AtbashCipher() {
    }

    public static String atbash(final String toEncrypt) {
        StringBuilder result = new StringBuilder(toEncrypt.length());
        for (char letter : toEncrypt.toCharArray()) {
            if (Character.isLetter(letter)) {
                result.append(Character.toChars((Character.isLowerCase(letter) ? 'z' + 'a' : 'Z' + 'A') - letter));
            } else {
                result.append(letter);
            }
        }
        return result.toString();
    }
}
