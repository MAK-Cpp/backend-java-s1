package edu.hw3.tasks.task5;

import org.jetbrains.annotations.NotNull;

public record Contact(String name, String surname) implements Comparable<Contact> {
    Contact(final String[] fio) {
        this((0 < fio.length ? fio[0] : null), (1 < fio.length ? fio[1] : null));
    }

    @Override
    public int compareTo(@NotNull Contact o) {
        return (surname == null ? name : surname).compareTo((o.surname == null ? o.name : o.surname));
    }
}
