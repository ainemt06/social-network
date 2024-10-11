import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

import java.util.List;

import network.Network;
import person.Person;


class NetworkTest {
    

    Network network;
    Person alice;
    Person bob;
    Person charlie;
    Person dave;

    @BeforeEach    
    void setUp() {

        network = new Network();
        alice = new Person("0", "Alice");
        bob = new Person("1","Bob");
        charlie = new Person("2", "Charlie");
        dave = new Person("3","Dave");
    }

    @Test
    
    void testAddEdge() {
        network.addEdge(alice, bob);
        assertTrue(alice.getConnections().contains(bob));
        assertTrue(bob.getConnections().contains(alice));
        assertEquals(1, alice.getConnectionsSize());
        assertEquals(1, bob.getConnectionsSize());
    }

    @Test
    
    void testRemoveEdge() {
        network.addEdge(alice, bob);
        network.removeEdge(alice, bob);
        assertFalse(alice.getConnections().contains(bob));
        assertFalse(bob.getConnections().contains(alice));
    }

    @Test
    
    void testFindInfluencers() {
        network.addEdge(alice, bob);
        network.addEdge(bob, charlie);
        network.addEdge(charlie, dave);

        List<Person> influencers = network.findInfluencers(2);
        assertEquals(4, influencers.size());  // All nodes should be part of the list
        assertTrue(influencers.contains(bob));  // Bob should be considered an influencer due to multiple connections
    }

    @Test
    
    void testFindShortestPath() {
        network.addEdge(alice, bob);
        network.addEdge(bob, charlie);
        network.addEdge(charlie, dave);
        network.addEdge(bob, dave);

        List<Person> path = network.findShortestPath(alice, dave);
        assertNotNull(path);
        assertEquals(3, path.size());
        assertEquals(alice, path.get(0));
        assertEquals(bob, path.get(1));
        assertEquals(dave, path.get(2));
    }

    @Test
    
    void testShortestPathNoPath() {
        network.addEdge(alice, bob);
        network.addEdge(charlie, dave);

        List<Person> path = network.findShortestPath(alice, dave);
        assertTrue(path.isEmpty());  // No path should be found between disconnected nodes
    }

    @Test
    
    void testNullPersonInAddEdge() {
        assertThrows(NullPointerException.class, () -> network.addEdge(null, bob));
        assertThrows(NullPointerException.class, () -> network.addEdge(alice, null));
    }

    @Test
    
    void testNullPersonInShortestPath() {
        assertThrows(NullPointerException.class, () -> network.findShortestPath(null, bob));
        assertThrows(NullPointerException.class, () -> network.findShortestPath(alice, null));
    }

    @Test
    
    void testPersonNotInNetwork() {
        Person eve = new Person("4","Eve");  // Eve is not part of the network
        assertThrows(NullPointerException.class, () -> network.findShortestPath(alice, eve));
    }
}

