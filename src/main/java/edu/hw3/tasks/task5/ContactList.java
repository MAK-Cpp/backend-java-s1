package edu.hw3.tasks.task5;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ContactList {
    private static final Pattern FIO_PATTERN = Pattern.compile("^ *([a-zA-Z]+) *([a-zA-Z]+)? *$");

    private ContactList() {
    }

    private static Contact parseContact(final String string) {
        Matcher matcher = FIO_PATTERN.matcher(string);
        if (matcher.find()) {
            return new Contact(matcher.group(1), matcher.group(2));
        } else {
            throw new IllegalArgumentException("worng FIO format: '" + string + "'");
        }
    }

    public static List<Contact> parseContacts(final Collection<String> names, final String order) {
        Comparator<Contact> comparator = switch (order) {
            case "ASC" -> Comparator.naturalOrder();
            case "DESC" -> Comparator.reverseOrder();
            default -> throw new IllegalArgumentException("wrong order of sorting: " + order);
        };
        return names.stream()
            .map(ContactList::parseContact)
            .sorted(comparator)
            .toList();
    }
}
