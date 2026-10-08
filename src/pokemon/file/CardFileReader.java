package pokemon.file;

import pokemon.card.CardDetails;
import pokemon.card.CardCatalogue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

// Reads cards.csv and turns each row into a CardDetails object
// Called from Main, returns a filled CardCatalogue
public class CardFileReader {

    // Entry point: reads <filePath> and returns the catalogue
    public static CardCatalogue load(String filePath) {

        // Empty container we fill up row by row
        CardCatalogue catalogue = new CardCatalogue();

        try {

            // Read the whole file, one String per line
            List<String> lines = Files.readAllLines(Paths.get(filePath)
            );

            // Check if the CSV file is empty
            if (lines.isEmpty()) {
                IO.println("CSV file is empty.");
                return catalogue;
            }

            // Counts rows we reject
            int skippedRows = 0;

            // Start at index 1 to skip the header
            for (int i = 1; i < lines.size(); i++) {

                String line = lines.get(i);

                // Skip empty lines
                if (line.isBlank()) {
                    continue;
                }

                // Split the row into fields via parseCsvLine()
                List<String> values = parseCsvLine(line);

                // Need at least the 14 fields we use, extra columns ignored
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

                // Create a CardDetails object from the CSV row
                CardDetails card = new CardDetails(
                        values.get(0),                                                                  // ID
                        values.get(1),                                                                  // Name
                        values.get(2),                                                                  // Number
                        values.get(3),                                                                  // Set ID
                        values.get(4),                                                                  // Set name
                        values.get(5),                                                                  // Series
                        values.get(6),                                                                  // Category
                        parseNullableInteger(values.get(7), "HP", i + 1),              // HP
                        values.get(8),                                                                  // Types
                        values.get(9),                                                                  // Evolution stage
                        parseNullableInteger(values.get(10), "Retreat cost", i + 1),    // Retreat cost
                        values.get(11),                                                                 // Rarity
                        values.get(12),                                                                 // Variants
                        values.get(13)                                                                  // Image path
                        // values.get(14) = illustrator, not loaded yet
                );
                // Add the card to the catalogue
                catalogue.addCard(card);
            }

            // Report rejected rows
            if (skippedRows > 0) {
                IO.println("Skipped " + skippedRows + " invalid rows.");
            }

        } catch (IOException e) {

            // File missing or unreadable, print error and keep going
            IO.println("Error reading CSV file: " + e.getMessage());
        }

        return catalogue;
    }

    // Converts a String to an Integer, returns null if empty or invalid
    private static Integer parseNullableInteger(
            String value,
            String fieldName,
            int rowNumber) {

        // Empty cell -> null
        if (value == null || value.isBlank()) {
            return null;
        }

        try {
            return Integer.parseInt(value.trim());

        } catch (NumberFormatException e) {
            // Not a number -> warn, but don't crash the load
            IO.println("Invalid " + fieldName
                    + " at row " + rowNumber
                    + ": " + e.getMessage());
            return null;
        }
    }

    // Splits one CSV line into fields, handling quoted commas
    private static List<String> parseCsvLine(String line) {

        // Finished fields of this row
        List<String> values = new ArrayList<>();

        // Field currently being built
        StringBuilder current = new StringBuilder();

        // True while between two " marks
        boolean insideQuotes = false;

        // Go through the line character by character
        for (int i = 0; i < line.length(); i++) {

            char character = line.charAt(i);

            if (character == '"') {

                // Handle escaped quotation marks
                if (insideQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    current.append('"');
                    i++;
                } else {
                    // Opening or closing quote
                    insideQuotes = !insideQuotes;
                }

            } else if (character == ',' && !insideQuotes) {

                // Comma outside quotes = end of field
                values.add(current.toString());
                current.setLength(0);

            } else {
                // Regular character, or comma inside quotes
                current.append(character);
            }
        }

        // Add the final value
        values.add(current.toString());

        return values;
    }
}
