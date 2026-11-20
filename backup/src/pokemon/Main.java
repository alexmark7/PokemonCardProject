package pokemon;

import javafx.application.Application;
import pokemon.card.CardCatalogue;
import pokemon.file.CardFileReader;
import pokemon.ui.ConsoleUI;
import pokemon.ui.JavaFXUI;

public class Main {

    static void main(String[] args) {

        // Pass --gui to open the JavaFX window instead of the console menu.
        if (args.length > 0 && args[0].equals("--gui")) {
            Application.launch(JavaFXUI.class, args);
            return;
        }

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
