package edu.hw7.tasks.task3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWritePersonDatabase implements PersonDatabase {
    private final ReadWriteLock lock = new ReentrantReadWriteLock(true);
    private final ArrayList<Person> persons;

    public ReadWritePersonDatabase() {
        persons = new ArrayList<>();
    }

    @Override
    public void add(Person person) {
        lock.writeLock().lock();
        try {
            persons.add(person);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public void delete(int id) {
        lock.writeLock().lock();
        try {
            persons.removeIf(x -> x.id() == id);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public List<Person> findByName(String name) {
        lock.readLock().lock();
        try {
            return persons.stream().filter(x -> x.name().equals(name)).toList();
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public List<Person> findByAddress(String address) {
        lock.readLock().lock();
        try {
            return persons.stream().filter(x -> x.address().equals(address)).toList();
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public List<Person> findByPhone(String phone) {
        lock.readLock().lock();
        try {
            return persons.stream().filter(x -> x.phoneNumber().equals(phone)).toList();
        } finally {
            lock.readLock().unlock();
        }
    }
}
