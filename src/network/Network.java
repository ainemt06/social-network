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
            return constructBFSPath(target, parentSource, parentTarget); 
        
        meetingPoint = nextBFSStep(edges, queueTarget, visitedTarget, visitedSource, parentTarget);

        if (meetingPoint.isPresent())
            return constructBFSPath(target, parentSource, parentTarget); 

        }


    return Collections.emptyList();
}

private Optional<Person> nextBFSStep(Map<Person, Set<Person>> graph, 
    Queue<Person> queue, Set<Person> visitedSelf, 
    Set<Person> visitedOther, Map<Person, Person> parentSelf) {


        //Placeholder list that allows streams to execute on the result
        Optional<Person> placeholderPerson = null;
        final List<Optional<Person>> result = new ArrayList<Optional<Person>>();
        result.add(placeholderPerson);

        queue.stream().forEach(node -> {

            result.set(0, edges.get(node).stream() //modifies value inside the final list
                .filter(neighbor -> visitedOther.contains(neighbor))
                .findFirst());
            
            Optional<Person> selfResult = edges.get(node).stream()
                .filter(neighbor -> visitedSelf.contains(neighbor))
                .findFirst();

            if (selfResult.isPresent()) {

                Person givenResult = selfResult.get();

                queue.add(givenResult);
                visitedSelf.add(givenResult);
                parentSelf.put(givenResult, node);  // Track the parent for path reconstruction
            }

        }); 
        
        return result.get(0);
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
