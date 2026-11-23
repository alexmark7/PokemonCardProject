package pokemon.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import pokemon.card.CardCatalogue;
import pokemon.file.CardFileReader;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;

public class JavaFXUI extends Application {

    private CardCatalogue catalogue;

    // Load the CSV before start() opens the catalogue window.
    @Override
    public void init() {
        catalogue = CardFileReader.load("data/cards.csv");
    }

    // Creates and opens the catalogue window.
    @Override
    public void start(Stage stage) {

        stage.setTitle("Pokémon Card Catalogue");

        var screen = Screen.getPrimary().getVisualBounds();
        double width = Math.min(1400, screen.getWidth() * 0.95);
        double height = Math.min(900, screen.getHeight() * 0.9);

        stage.setScene(new Scene(createView(catalogue), width, height));
        stage.setMinWidth(400);
        stage.setMinHeight(300);
        stage.show();
    }

    // Loads the Scene Builder layout for an existing catalogue. Call this on the JavaFX application thread.
    public BorderPane createView(CardCatalogue catalogue) {

        try {
            FXMLLoader loader = new FXMLLoader(locateView());
            BorderPane root = loader.load();
            CatalogueController controller = loader.getController();
            controller.setCatalogue(catalogue);
            return root;
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to load catalogue-view.fxml", exception);
        }
    }

    // Prefer the copy beside the compiled classes, then fall back to the source tree.
    private static URL locateView() throws IOException {

        URL resource = JavaFXUI.class.getResource("catalogue-view.fxml");

        if (resource != null) {
            return resource;
        }

        return Path.of("src/pokemon/ui/catalogue-view.fxml").toUri().toURL();
    }
}
