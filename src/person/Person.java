public class Person {
    private String id;
    private String name;
    private List<Person> connections;

    public Person(String id, String name) {
        this.id = id;
        this.name = name;
        connections = new ArrayList<Person>();
    }
}
