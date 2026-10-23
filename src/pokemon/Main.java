package pokemon;

import pokemon.card.CardCatalogue;
import pokemon.file.CardFileReader;
import pokemon.ui.ConsoleUI;

public class Main {

    static void main(String[] args) {

        CardCatalogue catalogue = CardFileReader.load("data/cards.csv");

        if (catalogue.size() == 0) {
            IO.println("No cards loaded.");
            return;
        }

        ConsoleUI console = new ConsoleUI(catalogue); // Creates new Object and passes "catalogue" into the object so it has the values.

        console.start();
    }
}