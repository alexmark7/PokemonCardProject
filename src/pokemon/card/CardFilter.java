package pokemon.card;

import java.util.ArrayList;
import java.util.List;

public class CardFilter {

    // Search
    private String name;
    private List<EvolutionStage> stages = new ArrayList<>();
    private Boolean holo;
    private List<PokemonType> types = new ArrayList<>();
    private List<String> expansions = new ArrayList<>();
    private List<String> sets = new ArrayList<>();

    // Numerical ranges
    private Integer minHP;
    private Integer maxHP;
    private Integer minRetreatCost;
    private Integer maxRetreatCost;

    public String getName() {
        return name;
    }

    public List<EvolutionStage> getStages() {
        return stages;
    }

    public Boolean getHolo() {
        return holo;
    }

    public List<PokemonType> getTypes() {
        return types;
    }

    public List<String> getExpansions() {
        return expansions;
    }

    public List<String> getSets() {
        return sets;
    }

    public Integer getMinHP() {
        return minHP;
    }

    public Integer getMaxHP() {
        return maxHP;
    }

    public Integer getMinRetreatCost() {
        return minRetreatCost;
    }

    public Integer getMaxRetreatCost() {
        return maxRetreatCost;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHolo(Boolean holo) {
        this.holo = holo;
    }

    public void setMinHP(Integer minHP) {
        this.minHP = minHP;
    }

    public void setMaxHP(Integer maxHP) {
        this.maxHP = maxHP;
    }

    public void setMinRetreatCost(Integer minRetreatCost) {
        this.minRetreatCost = minRetreatCost;
    }

    public void setMaxRetreatCost(Integer maxRetreatCost) {
        this.maxRetreatCost = maxRetreatCost;
    }
}
