package pokemon.collection;

import pokemon.card.CardDetails;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserCollection {

    private Map<CardDetails, Integer> ownedCards = new HashMap<>();

    public void addCard(CardDetails card) {
        // TODO: Add card or increase quantity
    }

    public void removeCard(CardDetails card) {
        // TODO: Remove one owned copy
    }

    public int getQuantity(CardDetails card) {
        return ownedCards.getOrDefault(card, 0);
    }

    public ArrayList<CardDetails> getOwnedCards() {
        return new ArrayList<>(ownedCards.keySet());
    }
}
