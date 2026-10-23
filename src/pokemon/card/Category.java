package pokemon.card;

// The card categories, in the order they sort
public enum Category {

    POKEMON("Pokémon"),
    TRAINER("Trainer"),
    ENERGY("Energy");

    private String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    // Turns text from the file into a category, or null if it is unknown
    public static Category parse(String value) {

        if (value == null) {
            return null;
        }

        String text = value.trim().toLowerCase();

        switch (text) {
            case "pokemon":
            case "pokémon":
                return POKEMON;
            case "trainer":
                return TRAINER;
            case "energy":
                return ENERGY;
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        return displayName;
    }
}
