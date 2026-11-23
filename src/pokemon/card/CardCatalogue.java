package pokemon.card;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

// Holds all the cards we loaded
public class CardCatalogue {

    private ArrayList<CardDetails> cards;

    public CardCatalogue() {
        cards = new ArrayList<>();
    }

    public void addCard(CardDetails card) {
        cards.add(card);
    }

    public ArrayList<CardDetails> getCards() {
        // Return a copy so adding or removing cards from it does not change the catalogue.
        // The copy contains the same CardDetails objects; only the list itself is copied.
        return new ArrayList<>(cards);
    }

    public int size() {
        return cards.size();
    }

    // Returns every expansion in the catalogue once, sorted by name and then ID
    public ArrayList<Expansion> getExpansions() {

        ArrayList<Expansion> expansions = new ArrayList<>();
        Set<String> addedSetIds = new HashSet<>();

        for (CardDetails card : cards) {

            if (addedSetIds.add(card.getSetId())) {
                expansions.add(new Expansion(card.getSetId(), card.getSetName()));
            }
        }

        // Sort by name, then by ID if two sets have the same name.
        expansions.sort(Comparator.comparing(Expansion::getName, String.CASE_INSENSITIVE_ORDER)
                .thenComparing(Expansion::getId, String.CASE_INSENSITIVE_ORDER));

        return expansions;
    }

    public static ArrayList<CardDetails> sortCards(
            List<CardDetails> cards,
            String sortBy,
            boolean ascending
    ) {
        ArrayList<CardDetails> results = new ArrayList<>(cards);

        Comparator<CardDetails> comparator = switch (sortBy.toLowerCase(Locale.ROOT)) {
            case "name" ->
                    Comparator.comparing(CardDetails::getName,
                            Comparator.nullsFirst(Comparator.naturalOrder()));

            case "stage" ->
                    Comparator.comparing(CardDetails::getEvolutionStage,
                            Comparator.nullsFirst(Comparator.naturalOrder()));

            case "variant" ->
                    Comparator.comparing(card -> card.isHolo() ? "Holo" : "Normal");

            case "hp" ->
                    Comparator.comparing(CardDetails::getHp,
                            Comparator.nullsFirst(Comparator.naturalOrder()));

            case "retreatcost" ->
                    Comparator.comparing(CardDetails::getRetreatCost,
                            Comparator.nullsFirst(Comparator.naturalOrder()));

            case "type" ->
                    Comparator.comparing(CardDetails::getTypesText);

            case "expansion" ->
                    Comparator.comparing(CardDetails::getSetName,
                            Comparator.nullsFirst(Comparator.naturalOrder()));

            case "set" ->
                    Comparator.comparing(CardDetails::getSetId,
                            Comparator.nullsFirst(Comparator.naturalOrder()));

            default ->
                    throw new IllegalArgumentException("Invalid sorting option");
        };

        if (!ascending) {
            comparator = comparator.reversed();
        }

        results.sort(comparator);
        return results;
    }

}
