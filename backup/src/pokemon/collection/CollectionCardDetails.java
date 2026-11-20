package pokemon.collection;

import pokemon.card.CardDetails;

public class CollectionCardDetails {

    private final CardDetails card;
    private final int quantity;
    private final Condition condition;

    public CollectionCardDetails(CardDetails card, int quantity, Condition condition) {
        this.card = card;
        this.quantity = quantity;
        this.condition = condition;
    }

    public CardDetails getCard() {
        return card;
    }

    public String getCardId() {
        return card.getId();
    }

    public int getQuantity() {
        return quantity;
    }

    public Condition getCondition() {
        return condition;
    }

}
