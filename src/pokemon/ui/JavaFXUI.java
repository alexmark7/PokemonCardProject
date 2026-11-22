package pokemon.ui;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.Screen;
import pokemon.card.CardCatalogue;
import pokemon.card.CardDetails;
import pokemon.card.Expansion;
import pokemon.file.CardFileReader;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.Locale;

public class JavaFXUI extends Application {

    private static final String PLACEHOLDER_IMAGE_PATH = "data/pokemon-card-images/placeholder.jpg";

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
                FXCollections.observableArrayList(catalogue.getCards())
        );

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

        ImageView cardImage = new ImageView();
        cardImage.setPreserveRatio(true);
        cardImage.setSmooth(true);
        cardImage.setAccessibleText("Card image");

        Label imagePlaceholder = new Label("Select a card to view its image.");
        imagePlaceholder.setWrapText(true);
        imagePlaceholder.setStyle("-fx-text-fill: #666666; -fx-font-size: 16px;");

        Image placeholderImage = loadImage(PLACEHOLDER_IMAGE_PATH);

        StackPane imagePreview = new StackPane(imagePlaceholder, cardImage);
        imagePreview.setPadding(new Insets(12));
        imagePreview.setMinSize(0, 0);
        imagePreview.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #cccccc;");

        // Use the full image column and resize smoothly when the window or dividers move.
        cardImage.fitWidthProperty().bind(
                Bindings.max(1, imagePreview.widthProperty().subtract(24))
        );
        cardImage.fitHeightProperty().bind(
                Bindings.max(1, imagePreview.heightProperty().subtract(24))
        );

        cardList.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldCard, card) -> {
                    details.setText(card == null ? "" : card.getFullDetails());
                    showCardImage(card, cardImage, placeholderImage, imagePlaceholder);
                }
        );

        Label resultCount = new Label();

        Runnable refreshResults = () -> updateResults(
                catalogue,
                results,
                searchField.getText(),
                expansionBox.getValue(),
                resultCount,
                cardList,
                details
        );

        searchField.textProperty().addListener(
                (observable, oldText, newText) -> refreshResults.run()
        );
        expansionBox.valueProperty().addListener(
                (observable, oldExpansion, newExpansion) -> refreshResults.run()
        );

        refreshResults.run();

        HBox filters = new HBox(10, searchField, expansionBox, clearButton);
        HBox.setHgrow(searchField, Priority.ALWAYS);

        // Give the picture its own full-height column beside the card's text details.
        SplitPane content = new SplitPane(cardList, imagePreview, details);
        content.setDividerPositions(0.25, 0.65);

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(12));
        root.setTop(filters);
        root.setCenter(content);
        root.setBottom(resultCount);
        BorderPane.setMargin(content, new Insets(12, 0, 12, 0));

        return root;
    }

    // Apply both filters and reset the selected card.
    private void updateResults(
            CardCatalogue catalogue,
            FilteredList<CardDetails> results,
            String searchText,
            Expansion expansion,
            Label resultCount,
            ListView<CardDetails> cardList,
            TextArea details
    ) {

        String search = searchText.trim().toLowerCase(Locale.ROOT);
        results.setPredicate(
                card -> card.getName().toLowerCase(Locale.ROOT).contains(search)
                        && (expansion == null || expansion.getId().equals(card.getSetId()))
        );

        resultCount.setText(results.size() + " of " + catalogue.size() + " cards");
        cardList.getSelectionModel().clearSelection();
        details.clear();
    }

    // Missing files, invalid paths and unsupported or damaged images use the placeholder.
    private void showCardImage(
            CardDetails card,
            ImageView cardImage,
            Image placeholderImage,
            Label placeholder
    ) {

        Image image = card == null ? null : loadImage(card.getImagePath());

        if (card != null && image == null) {
            image = placeholderImage;
        }

        // Replacing the image also clears the previous card's picture after deselection.
        cardImage.setImage(image);

        // Keep a text fallback in case the placeholder file itself is missing or damaged.
        placeholder.setText(card == null ? "Select a card to view its image." : "Image unavailable");
        placeholder.setVisible(image == null);
    }

    private static Image loadImage(String filePath) {

        if (filePath == null || filePath.isBlank()) {
            return null;
        }

        try {
            Image image = new Image(Path.of(filePath).toUri().toString());
            return image.isError() ? null : image;
        } catch (InvalidPathException exception) {
            return null;
        }
    }
}
