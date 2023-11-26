package edu.hw7.tasks.task3;

import java.util.ArrayList;
import java.util.List;

public class SynchronizedPersonDatabase implements PersonDatabase {
    private final ArrayList<Person> persons;

    public SynchronizedPersonDatabase() {
        persons = new ArrayList<>();
    }

    @Override
    public synchronized void add(Person person) {
        persons.add(person);
    }

    @Override
    public synchronized void delete(int id) {
        persons.removeIf(x -> x.id() == id);
    }

    @Override
    public synchronized List<Person> findByName(String name) {
        return persons.stream().filter(x -> x.name().equals(name)).toList();
    }

    @Override
    public synchronized List<Person> findByAddress(String address) {
        return persons.stream().filter(x -> x.address().equals(address)).toList();
    }

    @Override
    public synchronized List<Person> findByPhone(String phone) {
        return persons.stream().filter(x -> x.phoneNumber().equals(phone)).toList();
    }
}
