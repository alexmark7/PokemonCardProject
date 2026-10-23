package pokemon.file;

import pokemon.card.CardDetails;
import pokemon.card.CardCatalogue;
import pokemon.card.Category;
import pokemon.card.EvolutionStage;
import pokemon.card.PokemonType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

// Loads cards from a CSV file into a catalogue
public class CardFileReader {

    private static final String PLACEHOLDER_IMAGE_PATH = "pokemon-card-images/placeholder.svg";

    // Main calls this to load the cards
    public static CardCatalogue load(String filePath) {

        CardCatalogue catalogue = new CardCatalogue();

        try {

            // Read each line of the CSV
            List<String> lines = Files.readAllLines(Paths.get(filePath)
            );

            // Check if the CSV file is empty
            if (lines.isEmpty()) {
                IO.println("CSV file is empty.");
                return catalogue;
            }

            // Keep track of any rows we skip
            int skippedRows = 0;

            // Start at index 1 to skip the header
            for (int i = 1; i < lines.size(); i++) {

                String line = lines.get(i);

                // Skip empty lines
                if (line.isBlank()) {
                    continue;
                }

                // Keep an empty image-path field so we can replace it with the placeholder.
                String[] values = line.split(",", -1);

                // Each row must contain all 14 fields, including blank ones
                if (values.length != 14) {
                    IO.println("Invalid CSV at row " + (i + 1) + ": Expected 14 fields, found " + values.length);
                    skippedRows++;
                    continue;
                }

                // Check required fields
                if (values[0].isBlank() || values[1].isBlank()) {
                    IO.println("Missing card ID or name at row: " + (i + 1));
                    skippedRows++;
                    continue;
                }

                String imagePath = values[13].trim();

                if (imagePath.isBlank()) {
                    imagePath = PLACEHOLDER_IMAGE_PATH;
                }

                // Make a card from this row
                CardDetails card = new CardDetails(
                        values[0],                                                // ID
                        values[1],                                                // Name
                        values[2],                                                // Number
                        values[3],                                                // Set ID
                        values[4],                                                // Set name
                        values[5],                                                // Series
                        Category.parse(values[6]),                                // Category
                        parseNullableInteger(values[7], "HP", i + 1),              // HP
                        PokemonType.parseList(values[8]),                         // Types
                        EvolutionStage.parse(values[9]),                          // Evolution stage
                        parseNullableInteger(values[10], "Retreat cost", i + 1),   // Retreat cost
                        values[11],                                               // Rarity
                        values[12],                                               // Variants
                        imagePath                                                 // Image path
                );
                // Save it in the catalogue
                catalogue.addCard(card);
            }

            // Show how many rows were skipped
            if (skippedRows > 0) {
                IO.println("Skipped " + skippedRows + " invalid rows.");
            }

        } catch (IOException e) {

            // Show an error if the file couldn't be read
            IO.println("Error reading CSV file: " + e.getMessage());
        }

        return catalogue;
    }

    // Turns HP or retreat cost into a number, or null if it can't
    private static Integer parseNullableInteger(
            String value,
            String fieldName,
            int rowNumber) {

        // Blank cells have no number
        if (value == null || value.isBlank()) {
            return null;
        }

        try {
            return Integer.parseInt(value.trim());

        } catch (NumberFormatException e) {
            // Bad number? Show which row it came from
            IO.println("Invalid " + fieldName
                    + " at row " + rowNumber
                    + ": " + e.getMessage());
            return null;
        }
    }

}
