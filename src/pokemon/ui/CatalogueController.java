package pokemon.ui;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import pokemon.card.CardCatalogue;
import pokemon.card.CardDetails;
import pokemon.card.CardFilter;
import pokemon.card.Expansion;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;

// Controller for catalogue-view.fxml. It owns the filtering and image display logic.
public class CatalogueController {

    private static final String PLACEHOLDER_IMAGE_PATH = "data/pokemon-card-images/placeholder.jpg";

    @FXML
    private TextField searchField;

    @FXML
    private ComboBox<Expansion> expansionBox;

    @FXML
    private Button clearButton;

    @FXML
    private ListView<CardDetails> cardList;

    @FXML
    private StackPane imagePreview;

    @FXML
    private Label imagePlaceholder;

    @FXML
    private ImageView cardImage;

    @FXML
    private TextArea details;

    @FXML
    private Label resultCount;

    private CardCatalogue catalogue;
    private FilteredList<CardDetails> results;
    private Image placeholderImage;

    // Called by FXMLLoader after the controls have been injected.
    @FXML
    private void initialize() {

        cardList.setPlaceholder(new Label("No matching cards found."));

        // Match the clip to the displayed image so the corners follow it as it resizes.
        Rectangle imageClip = new Rectangle();
        imageClip.setArcWidth(38);
        imageClip.setArcHeight(38);
        imageClip.widthProperty().bind(cardImage.layoutBoundsProperty().map(Bounds::getWidth));
        imageClip.heightProperty().bind(cardImage.layoutBoundsProperty().map(Bounds::getHeight));
        cardImage.setClip(imageClip);

        // Use the full image column and resize smoothly when the window or dividers move.
        cardImage.fitWidthProperty().bind(
                Bindings.max(1, imagePreview.widthProperty().subtract(24))
        );
        cardImage.fitHeightProperty().bind(
                Bindings.max(1, imagePreview.heightProperty().subtract(24))
        );

        placeholderImage = loadImage(PLACEHOLDER_IMAGE_PATH);

        cardList.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldCard, card) -> {
                    details.setText(card == null ? "" : card.getFullDetails());
                    showCardImage(card);
                }
        );

        searchField.textProperty().addListener(
                (observable, oldText, newText) -> refreshResults()
        );
        expansionBox.valueProperty().addListener(
                (observable, oldExpansion, newExpansion) -> refreshResults()
        );
    }

    // Supplies the data after FXMLLoader has created the view.
    public void setCatalogue(CardCatalogue catalogue) {

        this.catalogue = catalogue;

        results = new FilteredList<>(
                FXCollections.observableArrayList(catalogue.getCards())
        );
        cardList.setItems(results);

        expansionBox.getItems().setAll(catalogue.getExpansions());
        expansionBox.getSelectionModel().clearSelection();

        refreshResults();
    }

    // Clears active filters. Their listeners refresh the list and the result count.
    @FXML
    private void onClearFilters() {
        searchField.clear();
        expansionBox.getSelectionModel().clearSelection();
    }

    // Apply active filters and reset the selected card. Listeners can fire before setCatalogue.
    private void refreshResults() {

        if (catalogue == null || results == null) {
            return;
        }

        CardFilter filter = new CardFilter();
        filter.setName(searchField.getText().trim());

        Expansion expansion = expansionBox.getValue();
        if (expansion != null) {
            filter.getSets().add(expansion.getId());
        }

        results.setPredicate(filter::matches);

        resultCount.setText(results.size() + " of " + catalogue.size() + " cards");
        cardList.getSelectionModel().clearSelection();
        details.clear();
    }

    // Missing files, invalid paths and unsupported or damaged images use the placeholder.
    private void showCardImage(CardDetails card) {

        Image image = card == null ? null : loadImage(card.getImagePath());

        if (card != null && image == null) {
            image = placeholderImage;
        }

        // Replacing the image also clears the previous card's picture after deselection.
        cardImage.setImage(image);

        // Keep a text fallback in case the placeholder file itself is missing or damaged.
        imagePlaceholder.setText(card == null ? "Select a card to view its image." : "Image unavailable");
        imagePlaceholder.setVisible(image == null);
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
