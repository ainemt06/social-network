import person.Person;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PersonTest {
    private Person createPerson(String i, String n) {
        Person testPerson = new Person(i, n);
        return testPerson;
    }

    @Test
    void nameCorrectlyAssigned() {
        Person bob = createPerson("0", "Bob");
        assertEquals("Bob", bob.getName());
    }

    @Test
    void idCorrectlyAssigned() {
        Person bob = createPerson("0", "Bob");
        assertEquals("0", bob.getId());
    }

    @Test
    void addedConnectionSuccessfully() {
        Person alice = createPerson("0", "Alice");
        Person brittany = createPerson("1", "Brittany");

        alice.addConnection(brittany);
        brittany.addConnection(alice);

        assertEquals("Alice", brittany.getConnections().first().getName());
        assertEquals("Brittany", alice.getConnections().first().getName());
    }

    @Test
    void ensureNoDuplicateConnections() {
        Person alice = createPerson("0", "Alice");
        Person brittany = createPerson("1", "Brittany");

        alice.addConnection(brittany);
        alice.addConnection(brittany);

        assertEquals(1, alice.getConnections().size());
    }

    @Test
    void removesConnectionsCorrectly() {
        Person alice = createPerson("0", "Alice");
        Person brittany = createPerson("1", "Brittany");

        alice.addConnection(brittany);
        brittany.addConnection(alice);

        alice.removeConnection(brittany);
        brittany.removeConnection(alice);

        assertEquals(0, alice.getConnections().size());

        assertEquals(0, brittany.getConnections().size());
    }

    @Test
    void ensureNoDuplicateDeletions() {
        Person alice = createPerson("0", "Alice");
        Person brittany = createPerson("1", "Brittany");

        alice.addConnection(brittany);
        alice.removeConnection(brittany);
        alice.removeConnection(brittany);

        assertEquals(0, alice.getConnections().size());
    }
}
