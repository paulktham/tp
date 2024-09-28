package seedu.duke;

import ui.UI;

public class Duke {
    /**
     * Main entry-point for the java.duke.Duke application.
     */
    public static void main(String[] args) {
        uiTestRun();
    }

    /**
     * Print greeting
     * Prompt for user input then waits
     * Echo user input
     * Print sample ASCII table
     * Print farewell message
     */
    public static void uiTestRun() {
        UI.printGreeting();
        UI.println("Type something...");
        String input = UI.getUserInput();

        UI.println("You typed: " + input);
        UI.println("Anyways, here is the sample table:");
        UI.printTable(); // Prints sample table to console
        UI.printBye();
    }
}
