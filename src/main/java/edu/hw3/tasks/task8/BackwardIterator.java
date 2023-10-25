package edu.hw3.tasks.task8;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class BackwardIterator<T> implements Iterator<T> {
    private final Collection<T> collection;
    private int cursor;

    public BackwardIterator(final Collection<T> collection) {
        this.collection = collection;
        cursor = collection.size() - 1;
    }

    @Override
    public boolean hasNext() {
        return cursor >= 0;
    }

    @Override
    public T next() {
        if (hasNext()) {
            final Object[] array = collection.toArray();
            if (array[cursor] == null) {
                cursor--;
                return null;
            }
            return (T) array[cursor--];
        } else {
            throw new NoSuchElementException();
        }
    }
}
