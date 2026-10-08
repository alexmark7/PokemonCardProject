package pokemon.card;

import java.util.ArrayList;

// Card collection with add/list/count
public class CardCatalogue {

    private ArrayList<CardDetails> cards;

    public CardCatalogue(){
        cards = new ArrayList<>();
    }
    public void addCard(CardDetails card) {
        cards.add(card);
    }
    public ArrayList<CardDetails> getCards(){
        return cards;
    }
    public int size() {
        return cards.size();
    }

    //search sort etc, to be added here

}
