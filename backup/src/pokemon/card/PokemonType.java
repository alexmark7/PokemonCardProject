package pokemon.card;

import java.util.ArrayList;

// The 11 card types, declared in alphabetical order
public enum PokemonType {

    COLORLESS("Colorless"),     // "Normal" in the requirements doc
    DARKNESS("Darkness"),
    DRAGON("Dragon"),
    FAIRY("Fairy"),
    FIGHTING("Fighting"),
    FIRE("Fire"),
    GRASS("Grass"),
    LIGHTNING("Lightning"),
    METAL("Metal"),
    PSYCHIC("Psychic"),
    WATER("Water");

    private String displayName;

    PokemonType(String displayName) {
        this.displayName = displayName;
    }

    // Turns one type name into a type, or null if it is unknown
    public static PokemonType parse(String value) {

        if (value == null) {
            return null;
        }

        String text = value.trim().toLowerCase();

        switch (text) {
            case "colorless":
                return COLORLESS;
            case "darkness":
                return DARKNESS;
            case "dragon":
                return DRAGON;
            case "fairy":
                return FAIRY;
            case "fighting":
                return FIGHTING;
            case "fire":
                return FIRE;
            case "grass":
                return GRASS;
            case "lightning":
                return LIGHTNING;
            case "metal":
                return METAL;
            case "psychic":
                return PSYCHIC;
            case "water":
                return WATER;
            default:
                return null;
        }
    }

    // Splits "Grass|Darkness" into a list of types
    public static ArrayList<PokemonType> parseList(String value) {

        ArrayList<PokemonType> types = new ArrayList<>();

        if (value == null || value.isBlank()) {
            return types;
        }

        String[] parts = value.split("\\|");

        for (String part : parts) {

            PokemonType type = parse(part);

            if (type != null && !types.contains(type)) {
                types.add(type);
            }
        }

        return types;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
