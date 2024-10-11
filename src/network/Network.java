package network;

import person.Person;

import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Objects;
import java.util.List;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Comparator;

public class Network {
    private final Map<Person, Set<Person>> edges;
    private final SortedSet<Person> nodes;

    public Network() {
        edges = new HashMap<Person, Set<Person>>();
        nodes = new TreeSet<Person>();
    }

public void addEdge(Person person1, Person person2) {
        Objects.requireNonNull(person1);
        Objects.requireNonNull(person2);
        
        nodes.add(person1);
        nodes.add(person2);
        
        person1.addConnection(person2);
        person2.addConnection(person1);

        if (!edges.containsKey(person1))
            edges.put(person1, person1.getConnections());
        if (!edges.containsKey(person2))
            edges.put(person2, person2.getConnections());

    }

public void removeEdge(Person person1, Person person2) {
    Objects.requireNonNull(person1);
    Objects.requireNonNull(person2);

    nodes.remove(person1);
    nodes.remove(person2);

    person1.removeConnection(person2);
    person2.removeConnection(person1);

}

public List<Person> findInfluencers(int k) {
    List<Person> influencerList = List.copyOf(nodes);

    influencerList.stream()
        .sorted(Comparator.comparingInt(Person::getConnectionsSize));

    return influencerList;
}

public List<Connection<Person>> findShortestPath(Person source, Person target) throws NullPointerException {
    //Uses bidirectional BFS to find the shortest path between two people
    
    Objects.requireNonNull(source);
    Objects.requireNonNull(target);

    
    if(!nodes.contains(source) || !nodes.contains(target))
        throw new NullPointerException("Person not contained in the network!");


    Queue<Person> queueSource = new LinkedList<Person>();
    Queue<Person> queueTarget = new LinkedList<Person>();

    Set<Person> visitedSource = new HashSet<Person>();
    Set<Person> visitedTarget = new HashSet<Person>();

    List<Connection<Person>> parentSource = new LinkedList<>();
    List<Connection<Person>> parentTarget = new LinkedList<>();

    return parentSource;
}

}
