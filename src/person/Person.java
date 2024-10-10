package person;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

public class Person {
    private final String id;
    private final String name;
    private List<Person> connections;

    public Person(String id, String name) {
        Optional<String> optionalId = Optional.ofNullable(id);
        String validId = optionalId.filter(s -> !s.isEmpty())
            .orElseThrow(() -> new IllegalArgumentException("Id must not be null or empty"));
        
            Optional<String> optionalName = Optional.ofNullable(id);
        String validName = optionalName.filter(s -> !s.isEmpty())
            .orElseThrow(() -> new IllegalArgumentException("Name must not be null or empty"));

        this.id = validId;
        this.name = validName;
        connections = new ArrayList<Person>();
    }

    public String getName() {
        return new String(name);
    }

    public String getId() {
        return new String(id);
    }

    public void addConnection(Person newPerson) {
        connections.add(newPerson);
    }
}
