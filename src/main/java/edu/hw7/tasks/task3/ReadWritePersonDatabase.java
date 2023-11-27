package edu.hw7.tasks.task3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWritePersonDatabase implements PersonDatabase {
    private final ReadWriteLock lock = new ReentrantReadWriteLock(true);
    private final HashMap<Integer, Person> personsIDs;
    private final HashMap<String, ArrayList<Person>> databaseByName;
    private final HashMap<String, ArrayList<Person>> databaseByAddress;
    private final HashMap<String, ArrayList<Person>> databaseByPhone;

    public ReadWritePersonDatabase() {
        personsIDs = new HashMap<>();
        databaseByName = new HashMap<>();
        databaseByAddress = new HashMap<>();
        databaseByPhone = new HashMap<>();
    }

    private void add(HashMap<String, ArrayList<Person>> database, Person person, String key) {
        if (database.containsKey(key)) {
            database.get(key).add(person);
        } else {
            database.put(key, new ArrayList<>(List.of(person)));
        }
    }

    @Override
    public void add(Person person) {
        lock.writeLock().lock();
        try {
            personsIDs.put(person.id(), person);
            add(databaseByName, person, person.name());
            add(databaseByAddress, person, person.address());
            add(databaseByPhone, person, person.phoneNumber());
        } finally {
            lock.writeLock().unlock();
        }
    }

    private void delete(HashMap<String, ArrayList<Person>> database, Person person, String key) {
        if (database.containsKey(key)) {
            database.get(key).remove(person);
        }
    }

    @Override
    public void delete(int id) {
        lock.writeLock().lock();
        try {
            Person toDelete = personsIDs.get(id);
            personsIDs.remove(id);
            delete(databaseByName, toDelete, toDelete.name());
            delete(databaseByAddress, toDelete, toDelete.address());
            delete(databaseByPhone, toDelete, toDelete.phoneNumber());
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public List<Person> findByName(String name) {
        lock.readLock().lock();
        try {
            return databaseByName.get(name);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public List<Person> findByAddress(String address) {
        lock.readLock().lock();
        try {
            return databaseByAddress.get(address);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public List<Person> findByPhone(String phone) {
        lock.readLock().lock();
        try {
            return databaseByPhone.get(phone);
        } finally {
            lock.readLock().unlock();
        }
    }
}
