import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import network.Connection;

public class ConnectionTest {
    
    private Connection<String, String> createConnection(String thing1, String thing2) {
        Connection<String, String> testConnection = new Connection<String, String>(thing1, thing2);
        return testConnection;
    }

    @Test
    void getFirstElement() {
        Connection<String, String> tester = createConnection("first element", "secondElement");

        assertEquals("first element", tester.getFirst());
    }

    @Test
    void getSecondElement() {
        Connection<String, String> tester = createConnection("first element", "second element");

        assertEquals("second element", tester.getSecond());
    }
}
