package pokemon.card;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class CardSorter {

    public static ArrayList<CardDetails> filterAndSort(
            List<CardDetails> allCards,
            List<CardDetails> ownedCards,
            boolean ownedOnly,
            CardFilter filters,
            String sortBy,
            boolean ascending) {

        // 1. Select collection
        ArrayList<CardDetails> selectedCards = ownedOnly
                ? new ArrayList<>(ownedCards)
                : new ArrayList<>(allCards);

        // 2. Apply selected filters
        applyFilters(selectedCards, filters);

        // 3. Select sorting criterion
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

        // 4. Sorting direction
        if (!ascending) {
            comparator = comparator.reversed();
        }

        // 5. Sort
        selectedCards.sort(comparator);

        return selectedCards;
    }

    private static void applyFilters(
            ArrayList<CardDetails> cards,
            CardFilter filters) {

        if (filters == null) {
            return;
        }

        Integer minHP = filters.getMinHP();
        Integer maxHP = filters.getMaxHP();

        if (minHP != null || maxHP != null) {
            cards.removeIf(card -> card.getHp() == null
                    || (minHP != null && card.getHp() < minHP)
                    || (maxHP != null && card.getHp() > maxHP));
        }

        // TODO: Filter by name
        // TODO: Filter by stages
        // TODO: Filter by holo
        // TODO: Filter by retreat cost range
        // TODO: Filter by types
        // TODO: Filter by expansions (set names)
        // TODO: Filter by sets (set IDs)
    }
}
