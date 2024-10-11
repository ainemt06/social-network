package network;

import java.util.Objects;

public class Connection<A> {

    private final A first;
    private final A second;

    public Connection(A thing1, A thing2) {
        Objects.requireNonNull(thing1);
        Objects.requireNonNull(thing2);

        first = thing1;
        second = thing2;
    }

    public A getFirst() {
        return first;
    }

    public A getSecond() {
        return second;
    }

    public boolean equals(Connection<A> connection) {
        if (this.first.equals(connection.getFirst()) && this.second.equals(connection.getSecond()))
            return true;
        else if (this.second.equals(connection.getFirst()) && this.first.equals(connection.getSecond()))
            return true;
        return false;
    }

}
