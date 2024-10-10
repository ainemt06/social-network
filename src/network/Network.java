package network;

import person.Person;

import java.util.SortedSet;
import java.util.TreeSet;

public class Network {
    private final SortedSet<Connection<Person, Person>> edges;
    private final SortedSet<Person> nodes;

    public Network() {
        edges = new TreeSet<Connection<Person, Person>>();
        nodes = new TreeSet<Person>();
    }

   // public addEdge(Person person1, Person person2) {

    //}
}
