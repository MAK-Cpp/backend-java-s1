package edu.hw3.tasks.task5;

import java.util.Arrays;
import java.util.Comparator;
import static java.lang.Math.min;

public final class ContactList {
    private static final Comparator<String> ASC_CMP = (o1, o2) -> {
        final String[] parsedO1 = o1.split(" ");
        final String[] parsedO2 = o2.split(" ");
        if (parsedO2.length > 2 || parsedO1.length > 2) {
            throw new IllegalArgumentException("wrong name format");
        }
        return parsedO1[min(parsedO1.length, 1)].compareTo(parsedO2[min(parsedO2.length, 1)]);
    };
    private static final Comparator<String> DESC_CMP = (o1, o2) -> -ASC_CMP.compare(o1, o2);

    private ContactList() {
    }

    public static Object[] parseContacts(final String[] names, final String order) {
        if (names == null) {
            return new Object[0];
        }
        return switch (order) {
            case "ASC" -> Arrays.stream(names).sorted(ASC_CMP).toArray();
            case "DESC" -> Arrays.stream(names).sorted(DESC_CMP).toArray();
            default -> throw new IllegalArgumentException("wrong order of sorting: " + order);
        };
    }
}
