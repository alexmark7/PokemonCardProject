package pokemon.file;

import pokemon.card.CardDetails;
import pokemon.card.CardCatalogue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

// Loads cards from a CSV file into a catalogue
public class CardFileReader {

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

                // Split this row into its fields
                List<String> values = parseCsvLine(line);

                // We need 14 fields; the illustrator column is ignored
                if (values.size() < 14) {
                    IO.println("Invalid CSV at row " + (i + 1) + ": Expected 14 fields, found " + values.size());
                    skippedRows++;
                    continue;
                }

                // Check required fields
                if (values.get(0).isBlank() || values.get(1).isBlank()) {
                    IO.println("Missing card ID or name at row: " + (i + 1));
                    skippedRows++;
                    continue;
                }

                // Make a card from this row
                CardDetails card = new CardDetails(
                        values.get(0),                                                                       // ID
                        values.get(1),                                                                       // Name
                        values.get(2),                                                                       // Number
                        values.get(3),                                                                       // Set ID
                        values.get(4),                                                                       // Set name
                        values.get(5),                                                                       // Series
                        values.get(6),                                                                       // Category
                        parseNullableInteger(values.get(7), "HP", i + 1),                 // HP
                        values.get(8),                                                                       // Types
                        values.get(9),                                                                       // Evolution stage
                        parseNullableInteger(values.get(10), "Retreat cost", i + 1),      // Retreat cost
                        values.get(11),                                                                      // Rarity
                        values.get(12),                                                                      // Variants
                        values.get(13)                                                                       // Image path
                        // values.get(14) = illustrator, not loaded yet
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

    // Splits a CSV row without splitting commas inside quotes
    private static List<String> parseCsvLine(String line) {

        List<String> values = new ArrayList<>();

        StringBuilder current = new StringBuilder();

        // Keep track of whether we're inside quotes
        boolean insideQuotes = false;

        for (int i = 0; i < line.length(); i++) {

            char character = line.charAt(i);

            if (character == '"') {

                // Two quotes together mean one actual quote
                if (insideQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    current.append('"');
                    i++;
                } else {
                    insideQuotes = !insideQuotes;
                }

            } else if (character == ',' && !insideQuotes) {

                // Only split at commas outside quotes
                values.add(current.toString());
                current.setLength(0);

            } else {
                current.append(character);
            }
        }

        // Add the last field too
        values.add(current.toString());

        return values;
    }
}
