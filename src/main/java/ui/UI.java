package ui;

import de.vandermeer.asciitable.AsciiTable;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;
import student.Student;
import university.University;
import university.UniversityRepository;

/**
 * Handle user interface FindMySEP application.
 * Methods to print messages, retrieve user input, display ASCII table
 * for student data.
 */
public class UI {
    private static final int LINE_LENGTH = 80;
    private static final String HORIZONTAL_LINE = "-".repeat(LINE_LENGTH);
    private final Scanner scanner;

    // Constructor for UI class
    public UI() {
        this.scanner = new Scanner(System.in);
        // Set the output text to be UTF-8 encoded to pass the Java CI tests
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
    }

    /**
     * Prints the given item to the console, wrapped by horizontal lines above
     * and below the specified item.
     *
     * @param item The item to be printed, which can be of any type.
     *             If the item is null, it prints "null".
     */
    public <T> void printResponse(T item) {
        String content = (item == null) ? "null" : item.toString();
        System.out.println(HORIZONTAL_LINE + "\n" + content + "\n" + HORIZONTAL_LINE);
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
        printResponse("Hi! Welcome to FindMySEP.");
    }

    /**
     * Prints a farewell message to the console.
     */
    public void cleanupAndExit() {
        printResponse("Adios, amigo!");
        scanner.close();
    }

    /**
     * Prints table displaying student information.
     * The table includes student IDs, GPAs, and preference rankings.
     *
     * @param studentList ArrayList of Student objects to be printed
     */
    public void printStudentList(ArrayList<Student> studentList) {
        if (studentList == null || studentList.isEmpty()) {
            printResponse("No students available for report generation.");
            return;
        }

        for (Student s : studentList) {
            System.out.println("Student ID: " + s.getId() + ", GPA: " + s.getGpa() +
                    ", Preferences: " + s.getUniPreferences());
        }

        AsciiTable at = new AsciiTable();
        at.addRule();
        at.setTextAlignment(TextAlignment.CENTER);
        at.setPaddingLeft(1);
        at.setPaddingRight(1);

        at.addRow("Student", "GPA", "Preference Rankings");
        at.addRule();

        for (Student s : studentList) {
            // Using Java Streams to create a comma-separated preference rankings string
            String uniPrefsString = s.getUniPreferences().stream()
                    .map(String::valueOf) // Convert each Integer to String
                    .collect(Collectors.joining(",")); // Join them with commas

            // Use the uniPrefsString for adding to the table
            at.addRow(s.getId(), s.getGpa(), uniPrefsString);
        }
        at.addRule();

        printResponse(at.render());
    }

    /**
     * Prints table displaying allocation results.
     * The table includes student IDs and respective allocation outcomes.
     *
     * @param studentList ArrayList of Student objects to be printed
     */
    public void generateReport(ArrayList<Student> studentList) {
        AsciiTable at = new AsciiTable();
        at.setTextAlignment(TextAlignment.LEFT);
        at.addRule();
        at.addRow("Student", "University Granted");
        at.addRule();

        for (Student s : studentList) {
            University allocated = UniversityRepository.getUniversityByIndex(s.getAllocatedUniversity());

            if (allocated != null) {
                at.addRow(s.getId(), allocated.getFullName());
            } else {
                at.addRow(s.getId(), "Not Allocated"); // Handle cases with no allocation
            }
        }
        at.addRule();

        printResponse("Here is the allocation report:\n" + at.render());
    }

    /**
     * Prints help menu to the console.
     */
    public void printHelp() {
        String helpMsg = """
            Here is the list of possible commands:
                add         Adds a student with the specified ID, GPA, and preference rankings.
                            Example: add id/A1234567I gpa/5.0 p/{13,61,43}
                            Note: PREFERENCE_RANKINGS should be enclosed in curly braces
    
                delete      Deletes the student with the specified ID from the list.
                            Example: delete id/A1234567I
    
                list        Lists all students currently in the system.
    
                allocate    Allocates students to available slots based on their preferences.
    
                generate    Generates a report of allocations and student data.
    
                exit        Exits the application.
    
                help        Displays this help message.
            """;
        printResponse(helpMsg);
    }
}
