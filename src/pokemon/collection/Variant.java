package pokemon.collection;

// A card's variant (printing).
// Standard, Holo and Reverse Holo sort first in this order,
// then the other variants alphabetically
public enum Variant {

    STANDARD("Standard"),
    HOLO("Holo"),
    REVERSE_HOLO("Reverse Holo"),

    FIRST_EDITION("First Edition");

    private String displayName;

    Variant(String displayName) {
        this.displayName = displayName;
    }

    // Turns text from the file into a variant, or null if it is unknown.
    // "normal" is the CSV name for Standard
    public static Variant parse(String value) {

        if (value == null) {
            return null;
        }

        String text = value.trim().toLowerCase();

        switch (text) {
            case "standard":
            case "normal":
                return STANDARD;
            case "holo":
                return HOLO;
            case "reverse":
            case "reverse holo":
                return REVERSE_HOLO;
            case "firstedition":
            case "first edition":
                return FIRST_EDITION;
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        return displayName;
    }
}
