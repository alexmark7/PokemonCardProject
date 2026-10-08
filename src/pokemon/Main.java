package pokemon;

import pokemon.card.CardDetails;
import pokemon.card.CardCatalogue;
import pokemon.file.CardFileReader;


public class Main {

    public static void main(String[] args) {

        // Tests CardDetails
        CardDetails card = new CardDetails(
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

        IO.println(card.getFullDetails());
        IO.println(card);
        IO.println();

        // Tests cards.csv import.
        // Reads data/cards.csv and stores all cards in the catalogue
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
        }
    }

}
