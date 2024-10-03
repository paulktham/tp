package seedu.duke;

import studentlist.StudentList;
import ui.UI;
import parser.Parser;

public class FindOurSEP {
    private UI ui;
    private Parser parser;
    private StudentList studentList;

    /**
     * Constructs a new Duke object.
     * This constructor initializes the Duke's UI and parser.
     */
    public FindOurSEP() {
        this.ui = new UI();
        this.studentList = new StudentList(this.ui);
        this.parser = new Parser(this.studentList,this.ui);
    }
    
    /**
     * Main entry-point for the java.duke.Duke application.
     */
    public static void main(String[] args) {
        FindOurSEP bob = new FindOurSEP();
        boolean isRunning = true;
        while (isRunning) {
            isRunning = bob.parser.parseInput();
        }
    }

    /**
     * Print greeting
     * Prompt for user input then waits
     * Echo user input
     * Print sample ASCII table
     * Print farewell message
     */
    public void uiTestRun() {
        this.ui.printGreeting();
        this.ui.println("Type something...");
        String input = this.ui.getUserInput();

        this.ui.println("You typed: " + input);
        this.ui.println("Anyways, here is the sample table:");
        this.ui.printTable(); // Prints sample table to console
        this.ui.printBye();
    }
}
