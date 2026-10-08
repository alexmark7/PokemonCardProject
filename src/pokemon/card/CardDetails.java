package pokemon.card;

//Creates a new Card object with all of its identifying details.
public class CardDetails {

    private String id;
    private String name;
    private String number;

    private String setId;
    private String setName;
    private String series;

    private String category;

    private Integer hp;
    private String types;
    private String evolutionStage;
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
            String category,
            Integer hp,
            String types,
            String evolutionStage,
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

    public String getCategory() {
        return category;
    }

    public Integer getHp() {
        return hp;
    }

    public String getTypes() {
        return types;
    }

    public String getEvolutionStage() {
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

    public String getFullDetails() {
        return "ID:              " + id + "\n"
                + "Name:            " + name + "\n"
                + "Number:          " + number + "\n"
                + "Set ID:          " + setId + "\n"
                + "Set name:        " + setName + "\n"
                + "Series:          " + series + "\n"
                + "Category:        " + category + "\n"
                + "HP:              " + hp + "\n"
                + "Types:           " + types + "\n"
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

