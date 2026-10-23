package pokemon;

import pokemon.card.CardCatalogue;
import pokemon.file.CardFileReader;
import pokemon.ui.ConsoleUI;

public class Main {

    static void main(String[] args) {

        // This path is relative to the working directory, which should be the project root.
        CardCatalogue catalogue = CardFileReader.load("data/cards.csv");

        if (catalogue.size() == 0) {
            IO.println("No cards loaded.");
            return;
        }

        // Give the console access to the loaded catalogue for searching and browsing.
        ConsoleUI console = new ConsoleUI(catalogue);

        console.start();
    }
}
