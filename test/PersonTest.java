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

        
    }
}
