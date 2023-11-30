package edu.hw7.tasks.task3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SynchronizedPersonDatabase implements PersonDatabase {
    private final HashMap<Integer, Person> personsIDs;
    private final HashMap<String, ArrayList<Person>> databaseByName;
    private final HashMap<String, ArrayList<Person>> databaseByAddress;
    private final HashMap<String, ArrayList<Person>> databaseByPhone;

    public SynchronizedPersonDatabase() {
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
    public synchronized void add(Person person) {
        personsIDs.put(person.id(), person);
        add(databaseByName, person, person.name());
        add(databaseByAddress, person, person.address());
        add(databaseByPhone, person, person.phoneNumber());
    }

    private void delete(HashMap<String, ArrayList<Person>> database, Person person, String key) {
        if (database.containsKey(key)) {
            database.get(key).remove(person);
        }
    }

    @Override
    public synchronized void delete(int id) {
        Person toDelete = personsIDs.get(id);
        personsIDs.remove(id);
        delete(databaseByName, toDelete, toDelete.name());
        delete(databaseByAddress, toDelete, toDelete.address());
        delete(databaseByPhone, toDelete, toDelete.phoneNumber());
    }

    @Override
    public synchronized List<Person> findByName(String name) {
        return databaseByName.get(name);
    }

    @Override
    public synchronized List<Person> findByAddress(String address) {
        return databaseByAddress.get(address);
    }

    @Override
    public synchronized List<Person> findByPhone(String phone) {
        return databaseByPhone.get(phone);
    }
}
