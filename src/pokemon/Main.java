package pokemon;

import pokemon.card.CardDetails;
import pokemon.card.CardCatalogue;
import pokemon.file.CardFileReader;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        // Tests CardDetails
        CardDetails singleCard = new CardDetails(
                "base1-4",
                "Charizard",
                "4",
                "base1",
                "Base Set",
                "Base",
                "Pokemon",
                120,
                "Fire",
                "Stage 2",
                3,
                "Rare Holo",
                "Normal",
                "images/base1-4.png"
        );

        IO.println(singleCard.getFullDetails());
        IO.println(singleCard);
        IO.println();

        // Load the cards from the CSV into the catalogue
        CardCatalogue catalogue = CardFileReader.load("data/cards.csv");
        IO.println("Loaded " + catalogue.size() + " cards.");

        if (catalogue.size() == 0) {
            return;
        }

        while  (true) {
            // User types a number to pick a card
            IO.println("Enter a card number (1-" + catalogue.size() + "):");

            int choice;

            try {
                choice = Integer.parseInt(IO.readln().trim());
            } catch (NumberFormatException e) {
                IO.println("Not a valid number.");
                continue;
            }

            if (choice < 1 || choice > catalogue.size()) {
                IO.println("Number out of range.");
                continue;
            }

            CardDetails selected = catalogue.getCards().get(choice - 1);

            IO.println("------------------------------------------------");
            IO.println(selected.getFullDetails());
            IO.println("------------------------------------------------");

            IO.println();

            ArrayList<CardDetails> results = catalogue.findByName(IO.readln("Enter Name: "));

            for (CardDetails card : results) {
                IO.println(card);
            }
        }
    }

}
