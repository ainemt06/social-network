package network;

import person.Person;

import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Objects;
import java.util.List;
import java.util.Comparator;

public class Network {
    private final SortedSet<Connection<Person>> edges;
    private final SortedSet<Person> nodes;

    public Network() {
        edges = new TreeSet<Connection<Person>>();
        nodes = new TreeSet<Person>();
    }

public void addEdge(Person person1, Person person2) {
        Objects.requireNonNull(person1);
        Objects.requireNonNull(person2);
        
        nodes.add(person1);
        nodes.add(person2);
        
        person1.addConnection(person2);
        person2.addConnection(person1);

        Connection<Person> newConnection = new Connection<Person>(person1, person2);

        edges.add(newConnection);

    }

public void removeEdge(Person person1, Person person2) {
    Objects.requireNonNull(person1);
    Objects.requireNonNull(person2);

    nodes.remove(person1);
    nodes.remove(person2);

    person1.removeConnection(person2);
    person2.removeConnection(person1);


    //Checks if there are any edges containing these two people, and deletes them
    edges.stream().forEach(edge -> {
        if (edge.equals(new Connection<Person>(person1, person2)))
            edges.remove(edge);
    });
}

public List<Person> findInfluencers(int k) {
    List<Person> influencerList = List.copyOf(nodes);

    influencerList.stream()
        .sorted(Comparator.comparingInt(Person::getConnectionsSize));

    return influencerList;
}

}
