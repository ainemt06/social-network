package person;

import java.util.Optional;


import java.util.SortedSet;
import java.util.TreeSet;

public class Person implements Comparable<Person> {
    private final String id;
    private final String name;
    private final SortedSet<Person> connections;

    public Person(String id, String name) {
        Optional<String> optionalId = Optional.ofNullable(id);
        String validId = optionalId.filter(s -> !s.isEmpty())
            .orElseThrow(() -> new IllegalArgumentException("Id must not be null or empty"));
        
            Optional<String> optionalName = Optional.ofNullable(name);
        String validName = optionalName.filter(s -> !s.isEmpty())
            .orElseThrow(() -> new IllegalArgumentException("Name must not be null or empty"));

        this.id = validId;
        this.name = validName;
        connections = new TreeSet<Person>();
    }

    public String getName() {
        return new String(name);
    }

    public String getId() {
        return new String(id);
    }

    public SortedSet<Person> getConnections() {
        return new TreeSet<Person>(connections);
    }

    public int getConnectionsSize() {
        return connections.size();
    }

    public void addConnection(Person newPerson) {
        connections.add(newPerson);
    }

    public void removeConnection(Person removedPerson) {
        connections.remove(removedPerson);
    }

    @Override
    public int compareTo(Person o) {
        return this.getName().compareTo(o.getName());
        
    }
}
