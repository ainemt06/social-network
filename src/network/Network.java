package network;

import person.Person;

import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Objects;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.Optional;
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

public static void main(String[] args) {

    Network network = new Network();
    Person alice = new Person("0", "Alice");
    Person bob = new Person("1","Bob");
    Person charlie = new Person("2", "Charlie");
    Person dave = new Person("3","Dave");

    network.addEdge(alice, bob);
    network.addEdge(bob, charlie);
    network.addEdge(charlie, dave);
    network.addEdge(bob, dave);

    List<Person> path = network.findShortestPath(alice, dave);
        
    path.stream().forEach(person -> {
        System.out.println(person.getName());
    });
}

public void addEdge(Person person1, Person person2) {
        Objects.requireNonNull(person1);
        Objects.requireNonNull(person2);
        
        nodes.add(person1);
        nodes.add(person2);
        
        person1.addConnection(person2);
        person2.addConnection(person1);

        //if (!edges.containsKey(person1))
            edges.put(person1, person1.getConnections());
        //if (!edges.containsKey(person2))
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

public List<Person> findShortestPath(Person source, Person target) throws NullPointerException {
    //Uses bidirectional BFS to find the shortest path between two people
    
    Objects.requireNonNull(source);
    Objects.requireNonNull(target);

    
    if(!nodes.contains(source) || !nodes.contains(target))
        throw new NullPointerException("Person not contained in the network!");


    Queue<Person> queueSource = new LinkedList<Person>();
    Queue<Person> queueTarget = new LinkedList<Person>();

    Set<Person> visitedSource = new HashSet<Person>();
    Set<Person> visitedTarget = new HashSet<Person>();

    Map<Person, Person> parentSource = new HashMap<>();
    Map<Person, Person> parentTarget = new HashMap<>();


    queueSource.add(source);
    queueTarget.add(target);
    visitedSource.add(source);
    visitedTarget.add(target);
    parentSource.put(source, null);  // Source has no parent
    parentTarget.put(target, null);  // Target has no parent

    while (!queueSource.isEmpty() && !queueTarget.isEmpty()) {
        Optional<Person> meetingPoint = 
            this.nextBFSStep(edges, queueSource, visitedSource, visitedTarget, parentSource);
        
        if(meetingPoint.isPresent())
            return constructBFSPath(meetingPoint.get(), parentSource, parentTarget); 
        
        meetingPoint = nextBFSStep(edges, queueTarget, visitedTarget, visitedSource, parentTarget);

        if (meetingPoint.isPresent())
            return constructBFSPath(meetingPoint.get(), parentSource, parentTarget); 

        }


    return Collections.emptyList();
}

private Optional<Person> nextBFSStep(Map<Person, Set<Person>> graph, 
    Queue<Person> queue, Set<Person> visitedSelf, 
    Set<Person> visitedOther, Map<Person, Person> parentSelf) {


        Optional<Person> result = null;
        
        while (!queue.isEmpty()) {
            Person node = queue.poll();

            result = edges.get(node).stream() 
                .filter(neighbor -> visitedOther.contains(neighbor))
                .findFirst();
            
            if (result.isPresent())
                return Optional.ofNullable(node);

            edges.get(node).stream()
                .filter(neighbor -> !visitedSelf.contains(neighbor))
                .forEach( neighborNode -> {
                    queue.add(neighborNode);
                    visitedSelf.add(neighborNode);
                    parentSelf.put(neighborNode, node);
                }
                );

            }
        
        return Optional.empty();
    }


private List<Person> constructBFSPath(Person meetingPoint,
    Map<Person, Person> parentSource, Map<Person, Person> parentTarget) {
        List<Person> path = new LinkedList<>();

        Person current = meetingPoint;

        while (current != null) {
            path.add(0, current);
            current = parentSource.get(current);
        }

        current = parentTarget.get(meetingPoint);
        
        while (current != null) {
            path.add(current);
            current = parentTarget.get(current);
        }

        return path;
    }

}
