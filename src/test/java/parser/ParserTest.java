package parser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import studentlist.StudentList;
import ui.Messages;
import ui.UI;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ui.UI.HORIZONTAL_LINE;

public class ParserTest {
    private UI ui;
    private StudentList studentList;
    private Parser parser;

    @BeforeEach
    void setUp() {
        ui = new UI();
        studentList = new StudentList(this.ui);
        this.parser = new Parser(this.studentList, this.ui);
    }

    @Test
    public void testAddCommand() {
        // Simulate user input for 'add' command
        String input = "add id/A1234567I gpa/5.0 p/{13,61,43}";

        // Run the parser
        boolean result = parser.parseInput(input);

        // Assert that the parser continues execution
        assertTrue(result);

        // Assert increment of student list
        assertEquals(1, this.studentList.getNumStudents());
    }

    @Test
    public void testDeleteCommand() {
        // Adds a student to the list
        parser.parseInput("add id/A1234567I gpa/5.0 p/{13,61,43}");

        // Simulate user input for 'delete' command
        String input = "delete A1234567I";

        // Run the parser
        boolean result = parser.parseInput(input);

        // Assert that the parser continues execution
        assertTrue(result);

        // Assert decrement of student list
        assertEquals(0, this.studentList.getNumStudents());
    }

    @Test
    public void testExitCommand() {
        // Set up the output stream to capture console output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        // Simulate user input for 'exit' command
        String input = "exit";

        // Run the parser
        boolean result = parser.parseInput(input);

        // Assert that the parser stops execution
        assertFalse(result);

        // Verify the expected output
        String expectedOutput = HORIZONTAL_LINE + "\n" + "Adios, amigo!" + "\n" + HORIZONTAL_LINE;
        assertEquals(expectedOutput,outContent.toString().trim());

        // Reset the original System.out
        System.setOut(originalOut);
    }


    @Test
    public void testHelpCommand() {
        // Set up the output stream to capture console output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        // Simulate user input for 'help' command
        String input = "help";

        // Run the parser
        boolean result = parser.parseInput(input);

        // Assert that the parser continues execution
        assertTrue(result);

        // Verify the expected output
        String expectedOutput = HORIZONTAL_LINE + "\n" + Messages.HELP.toString() + "\n" + HORIZONTAL_LINE;
        assertEquals(expectedOutput,outContent.toString().trim());

        // Reset the original System.out
        System.setOut(originalOut);
    }

    @Test
    public void testUnknownCommand() {
        // Set up the output stream to capture console output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        // Simulate user input for 'unknown' command
        String input = "8^jodja/!!! /add";

        // Run the parser
        boolean result = parser.parseInput(input);

        // Assert that the parser continues execution
        assertTrue(result);

        // Verify the expected output
        String expectedOutput = HORIZONTAL_LINE + "\n" + "Invalid command" + "\n" + HORIZONTAL_LINE;
        assertEquals(expectedOutput,outContent.toString().trim());

        // Reset the original System.out
        System.setOut(originalOut);
    }
}
