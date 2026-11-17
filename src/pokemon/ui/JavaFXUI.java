package pokemon.ui;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import pokemon.card.CardCatalogue;
import pokemon.card.CardDetails;
import pokemon.card.Expansion;
import pokemon.file.CardFileReader;

import java.util.Locale;

public class JavaFXUI extends Application {

    private CardCatalogue catalogue;

    // Load the CSV before starting the JavaFX application thread.
    @Override
    public void init() {
        catalogue = CardFileReader.load("data/cards.csv");
    }

    // Creates and opens the catalogue window.
    @Override
    public void start(Stage stage) {
        stage.setTitle("Pokémon Card Catalogue");
        stage.setScene(new Scene(createView(catalogue), 1000, 650));
        stage.show();
    }

    // Builds a view for an existing catalogue. Call this on the JavaFX application thread.
    public BorderPane createView(CardCatalogue catalogue) {
        TextField searchField = new TextField();
        searchField.setPromptText("Search cards by name");
        searchField.setAccessibleText("Card name");

        ComboBox<Expansion> expansionBox = new ComboBox<>();
        // A null selection means that cards from all expansions are included.
        expansionBox.setPromptText("All expansions");
        expansionBox.setAccessibleText("Expansion");
        expansionBox.getItems().setAll(catalogue.getExpansions());

        Button clearButton = new Button("Clear filters");
        clearButton.setOnAction(event -> {
            searchField.clear();
            expansionBox.getSelectionModel().clearSelection();
        });

        FilteredList<CardDetails> results = new FilteredList<>(
                FXCollections.observableArrayList(catalogue.getCards()));
        ListView<CardDetails> cardList = new ListView<>(results);
        cardList.setPlaceholder(new Label("No matching cards found."));
        cardList.setAccessibleText("Matching cards");

        TextArea details = new TextArea();
        details.setEditable(false);
        // Equal-width characters keep the padded values aligned in one column.
        details.setFont(Font.font("Monospaced", 13));
        details.setWrapText(false);
        details.setPromptText("Select a card to view its details.");
        details.setAccessibleText("Card details");

        cardList.getSelectionModel().selectedItemProperty().addListener((observable, oldCard, card) -> {
            details.setText(card == null ? "" : card.getFullDetails());
        });

        Label resultCount = new Label();

        // Both filters apply together; clearing them shows the full catalogue again.
        Runnable updateResults = () -> {
            String search = searchField.getText().trim().toLowerCase(Locale.ROOT);
            Expansion expansion = expansionBox.getValue();

            results.setPredicate(card -> card.getName().toLowerCase(Locale.ROOT).contains(search)
                    && (expansion == null || expansion.getId().equals(card.getSetId())));

            resultCount.setText(results.size() + " of " + catalogue.size() + " cards");
            cardList.getSelectionModel().clearSelection();
            details.clear();
        };

        searchField.textProperty().addListener((observable, oldText, newText) -> updateResults.run());
        expansionBox.valueProperty().addListener((observable, oldExpansion, newExpansion) -> updateResults.run());
        updateResults.run();

        HBox filters = new HBox(10, searchField, expansionBox, clearButton);
        HBox.setHgrow(searchField, Priority.ALWAYS);

        SplitPane content = new SplitPane(cardList, details);
        content.setDividerPositions(0.5);

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(12));
        root.setTop(filters);
        root.setCenter(content);
        root.setBottom(resultCount);
        BorderPane.setMargin(content, new Insets(12, 0, 12, 0));
        return root;
    }
}
