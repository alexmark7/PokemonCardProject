package pokemon.card;

import java.util.ArrayList;

// Holds the details for one card
public class CardDetails {

    private String id;
    private String name;
    private String number;

    private String setId;
    private String setName;
    private String series;

    private Category category;

    // Integer allows null when HP is missing or does not apply to the card.
    private Integer hp;
    private ArrayList<PokemonType> types;
    private EvolutionStage evolutionStage;
    // A missing retreat cost is null; a cost of 0 means retreating is free.
    private Integer retreatCost;
    private String rarity;

    private String variants;

    private String imagePath;

    public CardDetails(
            String id,
            String name,
            String number,
            String setId,
            String setName,
            String series,
            Category category,
            Integer hp,
            ArrayList<PokemonType> types,
            EvolutionStage evolutionStage,
            Integer retreatCost,
            String rarity,
            String variants,
            String imagePath)
    {
        this.id = id;
        this.name = name;
        this.number = number;
        this.setId = setId;
        this.setName = setName;
        this.series = series;
        this.category = category;
        this.hp = hp;
        this.types = types;
        this.evolutionStage = evolutionStage;
        this.retreatCost = retreatCost;
        this.rarity = rarity;
        this.variants = variants;
        this.imagePath = imagePath;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getSetId() {
        return setId;
    }

    public String getSetName() {
        return setName;
    }

    public String getSeries() {
        return series;
    }

    public Category getCategory() {
        return category;
    }

    public Integer getHp() {
        return hp;
    }

    public ArrayList<PokemonType> getTypes() {
        return types;
    }

    // Joins the type names for display, like "Fire, Water"
    public String getTypesText() {

        String text = "";

        for (PokemonType type : types) {

            if (!text.isEmpty()) {
                text += ", ";
            }

            text += type;
        }

        return text;
    }

    public EvolutionStage getEvolutionStage() {
        return evolutionStage;
    }

    public Integer getRetreatCost() {
        return retreatCost;
    }

    public String getRarity() {
        return rarity;
    }

    public String getVariants() {
        return variants;
    }


    public String getImagePath() {
        return imagePath;
    }

    // Formats all card details as a multiline string for console display.
    public String getFullDetails() {
        return "ID:              " + id + "\n"
                + "Name:            " + name + "\n"
                + "Number:          " + number + "\n"
                + "Set ID:          " + setId + "\n"
                + "Set name:        " + setName + "\n"
                + "Series:          " + series + "\n"
                + "Category:        " + category + "\n"
                + "HP:              " + hp + "\n"
                + "Types:           " + getTypesText() + "\n"
                + "Evolution stage: " + evolutionStage + "\n"
                + "Retreat cost:    " + retreatCost + "\n"
                + "Rarity:          " + rarity + "\n"
                + "Variants:        " + variants + "\n"
                + "Image path:      " + imagePath;
    }

    @Override
    public String toString() {
        return name + " #" + number + " - " + setName;
    }
}
