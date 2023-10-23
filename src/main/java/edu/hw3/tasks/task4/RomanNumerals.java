package edu.hw3.tasks.task4;

import java.util.Map;
import java.util.TreeMap;

public final class RomanNumerals {
    private static final int MAX_ROMANIAN_NUMBER = 3999;
    private static final TreeMap<Integer, String> PARTS_OF_ROMANIAN_NUMBER = new TreeMap<>(Map.ofEntries(
        Map.entry(-1000, "M"),
        Map.entry(-900, "CM"),
        Map.entry(-500, "D"),
        Map.entry(-400, "CD"),
        Map.entry(-100, "C"),
        Map.entry(-90, "XC"),
        Map.entry(-50, "L"),
        Map.entry(-40, "XL"),
        Map.entry(-10, "X"),
        Map.entry(-9, "IX"),
        Map.entry(-5, "V"),
        Map.entry(-4, "IV"),
        Map.entry(-1, "I")
    ));

    private RomanNumerals() {
    }

    public static String convertToRoman(final int number) {
        StringBuilder ans = new StringBuilder();
        if (number > MAX_ROMANIAN_NUMBER || number <= 0) {
            throw new RomanNumeralsException("Invalid number to convect to Roman");
        }
        int numberToConvert = -number;
        for (int key : PARTS_OF_ROMANIAN_NUMBER.keySet()) {
            while (numberToConvert <= key) {
                ans.append(PARTS_OF_ROMANIAN_NUMBER.get(key));
                numberToConvert -= key;
            }
        }
        return ans.toString();
    }
}
