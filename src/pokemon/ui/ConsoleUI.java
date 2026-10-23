package pokemon.ui;

import pokemon.card.CardCatalogue;
import pokemon.card.CardDetails;
import pokemon.card.Expansion;

import java.util.ArrayList;
import java.util.List;

public class ConsoleUI {

    private CardCatalogue catalogue;

    public ConsoleUI(CardCatalogue catalogue) {
        this.catalogue = catalogue;
    }

    public void start() {

        while (true) {

            IO.println();
            IO.println("POKÉMON CARD CATALOGUE");
            IO.println("Total cards: " + catalogue.size());
            IO.println();
            IO.println("1. Search by name");
            IO.println("2. Browse by expansion");
            IO.println("0. Exit");

            String choice = IO.readln("Choose (or type a name): ");

            switch (choice) {

                case "1" -> searchByName();

                case "2" -> browseByExpansion();

                case "0" -> {
                    IO.println("Goodbye!");
                    return;
                }

                // Anything else is treated as a name search
                default -> showSearchResults(choice.trim()); // Brings choice value into showSearchResults method
            }
        }
    }

    private void searchByName() {

        String search = IO.readln("Enter name: ").trim();

        showSearchResults(search);
    }

    // Searches names and shows the results, or a message if nothing matched
    private void showSearchResults(String search) {

        if (search.isBlank()) {
            IO.println("Please enter a name or list item.");
            return;
        }

        List<CardDetails> results = catalogue.findByName(search);

        displayResults(results);
    }

    private void displayResults(List<CardDetails> results) {

        if (results.isEmpty()) {
            IO.println("No matching cards found.");
            return;
        }

        // Pagination
        int pageSize = 10;
        int currentPage = 0;

        int totalPages = (results.size() + pageSize - 1) / pageSize;

        while (true) {

            int start = currentPage * pageSize;
            int end = Math.min(start + pageSize, results.size());

            IO.println();
            IO.println("----------------------------------------");
            IO.println("Found " + results.size() + " matching cards.");
            IO.println("Page " + (currentPage + 1) + " of " + totalPages);
            IO.println("----------------------------------------");

            // Display cards on current page
            for (int i = start; i < end; i++) {
                IO.println((i + 1) + ". " + results.get(i));
            }

            IO.println();

            // Navigation
            if (currentPage > 0) {
                IO.println("P. Previous page");
            }

            if (currentPage < totalPages - 1) {
                IO.println("N. Next page");
            }

            IO.println("0. Back");

            String input = IO.readln("Select card: ").trim();

            // Next page
            if (input.equalsIgnoreCase("N")) {

                if (currentPage < totalPages - 1) {
                    currentPage++;
                } else {
                    IO.println("Already on the last page.");
                }

                continue;
            }

            // Previous page
            if (input.equalsIgnoreCase("P")) {

                if (currentPage > 0) {
                    currentPage--;
                } else {
                    IO.println("Already on the first page.");
                }

                continue;
            }

            int choice;

            try {
                choice = Integer.parseInt(input);

            } catch (NumberFormatException e) {
                IO.println("Not a valid number.");
                continue;
            }

            // Return to main menu
            if (choice == 0) {
                return;
            }

            // Check selection is on current page
            if (choice < start + 1 || choice > end) {
                IO.println("Number out of range.");
                continue;
            }

            // Select card from search results
            CardDetails selected = results.get(choice - 1);

            showCardDetails(selected);

            IO.readln("Press Enter to return to results...");
        }
    }

    private void browseByExpansion() {

        List<Expansion> allExpansions = catalogue.getExpansions();
        List<Expansion> expansions = allExpansions;

        int pageSize = 10;
        int currentPage = 0;

        // Keep showing the expansion list until the user goes back
        while (true) {

            int totalPages = (expansions.size() + pageSize - 1) / pageSize;

            int start = currentPage * pageSize;
            int end = Math.min(start + pageSize, expansions.size());

            IO.println();
            IO.println("----------------------------------------");
            IO.println("Expansions: " + expansions.size());
            IO.println("Page " + (currentPage + 1) + " of " + totalPages);
            IO.println("----------------------------------------");

            // Display expansions on current page
            for (int i = start; i < end; i++) {
                Expansion expansion = expansions.get(i);
                IO.println((i + 1) + ". " + expansion.getName() + " [" + expansion.getId() + "]");
            }

            IO.println();

            // Navigation
            if (currentPage > 0) {
                IO.println("P. Previous page");
            }

            if (currentPage < totalPages - 1) {
                IO.println("N. Next page");
            }

            IO.println("S. Search expansions");
            IO.println("0. Back");

            String input = IO.readln("Select expansion: ").trim();

            // Next page
            if (input.equalsIgnoreCase("N")) {

                if (currentPage < totalPages - 1) {
                    currentPage++;
                } else {
                    IO.println("Already on the last page.");
                }

                continue;
            }

            // Previous page
            if (input.equalsIgnoreCase("P")) {

                if (currentPage > 0) {
                    currentPage--;
                } else {
                    IO.println("Already on the first page.");
                }

                continue;
            }

            // Search expansions (blank shows all)
            if (input.equalsIgnoreCase("S")) {

                String search = IO.readln("Enter expansion name (blank shows all): ").trim();

                List<Expansion> matches = findExpansions(allExpansions, search);

                if (matches.isEmpty()) {
                    IO.println("No matching expansions found.");
                } else {
                    expansions = matches;
                    currentPage = 0;
                }

                continue;
            }

            int choice;

            try {
                choice = Integer.parseInt(input);

            } catch (NumberFormatException e) {
                IO.println("Not a valid number.");
                continue;
            }

            // Return to main menu
            if (choice == 0) {
                return;
            }

            // Check selection is on current page
            if (choice < start + 1 || choice > end) {
                IO.println("Number out of range.");
                continue;
            }

            // Show the cards of the picked expansion
            Expansion selected = expansions.get(choice - 1);

            List<CardDetails> results = catalogue.findByExpansion(selected.getId());

            displayResults(results);
        }
    }

    // Filters the expansion list by name
    private List<Expansion> findExpansions(List<Expansion> expansions, String search) {

        List<Expansion> matches = new ArrayList<>();

        for (Expansion expansion : expansions) {

            if (search.isBlank() || expansion.getName().toLowerCase().contains(search.toLowerCase())) {
                matches.add(expansion);
            }
        }

        return matches;
    }

    private void showCardDetails(CardDetails selected) {

        IO.println();
        IO.println("----------------------------------------");
        IO.println(selected.getFullDetails());
        IO.println("----------------------------------------");
        IO.println();
    }
}