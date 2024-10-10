package network;

import java.util.Objects;

public class Connection<A, B> {

    private final A first;
    private final B second;

    public Connection(A thing1, B thing2) {
        Objects.requireNonNull(thing1);
        Objects.requireNonNull(thing2);

        first = thing1;
        second = thing2;
    }

    public A getFirst() {
        return first;
    }

    public B getSecond() {
        return second;
    }

}
