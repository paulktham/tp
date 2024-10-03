package ui;

import de.vandermeer.asciitable.AsciiTable;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * Handle user interface FindMySEP application.
 * Methods to print messages, retrieve user input, display ASCII table
 * for student data.
 */
public class UI {
    private final Scanner scanner;

    // Constructor for UI class
    public UI() {
        this.scanner = new Scanner(System.in);
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
    }

    /**
     * Prints the given item to the console.
     *
     * @param item The item to be printed, which can be of any type.
     *             If the item is null, it prints "null".
     */
    public <T> void println(T item) {
        if (item == null) {
            System.out.println("null");
        } else {
            System.out.println(item);
        }
    }

    /**
     * Retrieves a line of user input from the console.
     *
     * @return The line of input entered by the user, or an empty string
     *         if an error occurs while reading input.
     */
    public String getUserInput() {
        try {
            return scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Error reading input: " + e.getMessage());
            return ""; // Return an empty string
        }
    }

    /**
     * Prints a greeting message to the console.
     */
    public void printGreeting() {
        println("Hi! Welcome to FindMySEP.");
    }

    /**
     * Prints a farewell message to the console.
     */
    public void printBye() {
        println("Goodbye, amigo!");
    }

    /**
     * Prints a sample ASCII table displaying student information.
     * The table includes student IDs, GPAs, and preference rankings.
     */
    public void printTable() {
        AsciiTable table = new AsciiTable();
        table.addRule();
        table.addRow("Student", "GPA", "Preference Rankings");
        table.addRule();
        table.addRow("A1234567I", "5.0", "13, 49, 61");
        table.addRow("A2113211X", "4.9", "61");
        table.addRule();
        println(table.render());
    }

    public void printStudentList() {
        // to-do
    }

    public void addStudent() {
        // to-do
    }

    public void deleteStudent() {
        // to-do
    }

    public void generateReport() {
        // to-do
    }

    public void printHelp() {
        // to-do
    }
}
