package pokemon.card;

// One expansion (set) from the catalogue
public class Expansion {

    private String id;
    private String name;

    public Expansion(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
