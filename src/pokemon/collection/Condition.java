package pokemon.collection;

// The six conditions a card can be in, in the order they sort
public enum Condition {

    MINT("Mint"),
    NEAR_MINT("Near Mint"),
    EXCELLENT("Excellent"),
    GOOD("Good"),
    PLAYED("Played"),
    DAMAGED("Damaged");

    private String displayName;

    Condition(String displayName) {
        this.displayName = displayName;
    }

    // Turns text from the saved file into a condition, or null if it is unknown
    public static Condition parse(String value) {

        if (value == null) {
            return null;
        }

        String text = value.trim().toLowerCase();

        switch (text) {
            case "mint":
                return MINT;
            case "near mint":
                return NEAR_MINT;
            case "excellent":
                return EXCELLENT;
            case "good":
                return GOOD;
            case "played":
                return PLAYED;
            case "damaged":
                return DAMAGED;
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        return displayName;
    }
}
