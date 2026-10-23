package pokemon.card;

import java.util.ArrayList;

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

        for (CardDetails card : cards) {

            // Skip sets that are already in the list
            boolean alreadyAdded = false;

            for (Expansion expansion : expansions) {

                if (expansion.getId().equals(card.getSetId())) {
                    alreadyAdded = true;
                    break;
                }
            }

            if (!alreadyAdded) {
                expansions.add(new Expansion(card.getSetId(), card.getSetName()));
            }
        }

        // Bubble sort by name, then by ID if two sets have the same name
        for (int i = 0; i < expansions.size() - 1; i++) {

            for (int j = 0; j < expansions.size() - 1 - i; j++) {

                Expansion first = expansions.get(j);
                Expansion second = expansions.get(j + 1);

                boolean swap = false;

                if (first.getName().compareToIgnoreCase(second.getName()) > 0) {
                    swap = true;
                } else if (first.getName().equalsIgnoreCase(second.getName())
                        && first.getId().compareToIgnoreCase(second.getId()) > 0) {
                    swap = true;
                }

                if (swap) {
                    expansions.set(j, second);
                    expansions.set(j + 1, first);
                }
            }
        }

        return expansions;
    }

    // Searches card names, the whole name or part of it
    public ArrayList<CardDetails> findByName(String search) {

        ArrayList<CardDetails> results = new ArrayList<>();

        for (CardDetails card : cards) {

            if (card.getName().toLowerCase().contains(search.toLowerCase())) {
                results.add(card);
            }
        }
        return results;
    }

    // Returns every card in one expansion
    public ArrayList<CardDetails> findByExpansion(String setId) {

        ArrayList<CardDetails> results = new ArrayList<>();

        for (CardDetails card : cards) {

            if (card.getSetId().equals(setId)) {
                results.add(card);
            }
        }
        return results;
    }

}
