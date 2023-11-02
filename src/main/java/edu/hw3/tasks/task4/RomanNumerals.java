package edu.hw3.tasks.task4;

import java.util.TreeMap;

@SuppressWarnings("checkstyle:MagicNumber")
public final class RomanNumerals {
    private static final int MAX_ROMANIAN_NUMBER = 3999;
    private static final TreeMap<Integer, String> PARTS_OF_ROMANIAN_NUMBER =
        new TreeMap<>((x, y) -> Integer.compare(y, x));

    static {
        PARTS_OF_ROMANIAN_NUMBER.put(1000, "M");
        PARTS_OF_ROMANIAN_NUMBER.put(900, "CM");
        PARTS_OF_ROMANIAN_NUMBER.put(500, "D");
        PARTS_OF_ROMANIAN_NUMBER.put(400, "CD");
        PARTS_OF_ROMANIAN_NUMBER.put(100, "C");
        PARTS_OF_ROMANIAN_NUMBER.put(90, "XC");
        PARTS_OF_ROMANIAN_NUMBER.put(50, "L");
        PARTS_OF_ROMANIAN_NUMBER.put(40, "XL");
        PARTS_OF_ROMANIAN_NUMBER.put(10, "X");
        PARTS_OF_ROMANIAN_NUMBER.put(9, "IX");
        PARTS_OF_ROMANIAN_NUMBER.put(5, "V");
        PARTS_OF_ROMANIAN_NUMBER.put(4, "IV");
        PARTS_OF_ROMANIAN_NUMBER.put(1, "I");
    }

    private RomanNumerals() {
    }

    public static String convertToRoman(final int number) {
        StringBuilder ans = new StringBuilder();
        if (number > MAX_ROMANIAN_NUMBER || number <= 0) {
            throw new RomanNumeralsException("Invalid number to convect to Roman");
        }
        int numberToConvert = number;
        for (int key : PARTS_OF_ROMANIAN_NUMBER.keySet()) {
            while (numberToConvert >= key) {
                ans.append(PARTS_OF_ROMANIAN_NUMBER.get(key));
                numberToConvert -= key;
            }
        }
        return ans.toString();
    }
}
