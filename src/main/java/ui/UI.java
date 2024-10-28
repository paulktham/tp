package ui;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciitable.CWC_LongestLine;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

import exceptions.SEPEmptyException;
import student.Student;
import university.University;
import university.UniversityRepository;

/**
 * Handles user interface FindMySEP application.
 * Methods to print messages, retrieve user input, display ASCII table
 * for student data.
 */
public class UI {
    private static final int LINE_LENGTH = 80;
    public static final String HORIZONTAL_LINE = "-".repeat(LINE_LENGTH);
    private final Scanner scanner;

    /**
     * Constructor for UI class
     */
    public UI() {
        this.scanner = new Scanner(System.in);
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
            return scanner.nextLine().trim();
        } catch (Exception e) {
            printResponse("Error reading input: " + e.getMessage());
            return ""; // Return an empty string
        }
    }

    /**
     * Prints a greeting message to the console.
     */
    public void sayHi() {
        printResponse(Messages.WELCOME);
    }

    /**
     * Prints a farewell message to the console.
     */
    public void cleanupAndExit() {
        printResponse("Do you want to save your results?");
    }

    /**
     * Prints table displaying student information.
     * The table includes student IDs, GPAs, and preference rankings.
     *
     * @param studentList ArrayList of Student objects to be printed
     * @throws SEPEmptyException if the student list is null or empty.
     */
    public void printStudentList(ArrayList<Student> studentList) throws SEPEmptyException {
        if (studentList == null || studentList.isEmpty()) {
            throw SEPEmptyException.rejectEmptyStudentList();
        }

        AsciiTable at = new AsciiTable();
        at.addRule();
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

        at.setTextAlignment(TextAlignment.CENTER);
        at.setPaddingLeft(3);
        at.setPaddingRight(3);

        at.getRenderer().setCWC(new CWC_LongestLine());
        printResponse("Here is the list:\n" + at.render());
    }

    /**
     * Prints a buffering message when the algorithm is running.
     * Upon algorithm completion, replaces the buffering message with a
     * completed message.
     */
    public void printAllocatingMessage() {
        System.out.println(HORIZONTAL_LINE);
        System.out.println("\r" + Messages.ALLOCATE_COMPLETE);
        System.out.println(HORIZONTAL_LINE);
    }

    /**
     * Prints table displaying allocation results.
     * The table includes student IDs and respective allocation outcomes.
     *
     * @param studentList ArrayList of Student objects to be printed
     * @throws SEPEmptyException If the student list is null or empty,
     *     indicating that there are no students to generate.
     */
    public void generateReport(ArrayList<Student> studentList) throws SEPEmptyException {
        if (studentList == null || studentList.isEmpty()) {
            throw SEPEmptyException.rejectEmptyStudentList();
        }

        AsciiTable at = new AsciiTable();
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

    public void printConfigMessage() {
        System.out.println(HORIZONTAL_LINE);
        System.out.println(Messages.WELCOME);
        System.out.println("Would you like to:");
        System.out.println("1. Manually input students data");
        System.out.println("2. Upload a file (.csv, .txt, .json)");
        System.out.println("Please choose 1 or 2 or exit :)");
        System.out.println(HORIZONTAL_LINE);
    }

    public void printProcessError() {
        printResponse("Process error! Please ensure file is formatted correctly before retrying. Good day!");
    }

    public void printFileLoadSuccessMessage() {
        printResponse("File loaded successfully! Let's begin!");
    }

    public boolean isValidOption(String option) {
        return option.equals("1") || option.equals("2") || option.equals("exit");
    }

    public String promptFilePath() {
        String command = pollInitialInput();
        return processInput(command);
    }

    public String pollInitialInput() {
        String input = getUserInput();
        while (!isValidOption(input)) {
            printResponse("Boss, type 1 or 2 or exit only leh!");
            input = getUserInput().toLowerCase();
        }
        return input;
    }

    public String processFilePathInput() {
        String input = getUserInput();
        while (input.isEmpty()) {
            printResponse("Filepath cannot be empty!");
            input = getUserInput();
        }
        return input;
    }

    public String processInput(String input) {
        if (input.equals("exit")) {
            cleanupAndExit();
            System.exit(0);
        }
        if (input.equals("2")) {
            printResponse("Please enter the ABSOLUTE path to the file: ");
            return processFilePathInput();
        }
        sayHi();
        return "";
    }

    public boolean isValidSaveChoice(String input) {
        return input.equals("csv") || input.equals("txt") || input.equals("json");
    }

    public String getSaveChoice() {
        printResponse("Please choose a file type (CSV, JSON, TXT) to save your results.");
        String input = getUserInput().toLowerCase();
        while (!isValidSaveChoice(input)) {
            printResponse("Please enter a valid (CSV, JSON, TXT) save choice!");
            input = getUserInput();
        }
        return input;
    }

    public boolean toSave() {
        String save = getUserInput().toLowerCase();
        while (!(save.equals("yes") || save.equals("no"))) {
            printResponse("Please enter a valid command (Yes/No)!");
            save = getUserInput().toLowerCase();
        }
        return save.equals("yes");
    }
}
