package parser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import student.Student;
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
    public void testStatAvgCommand() {
        // Simulate user input for 'stats -avggpa' command
        parser.parseInput("add id/A1234567I gpa/5.0 p/{36,61,43}");
        parser.parseInput("add id/A1234567J gpa/3.0 p/{36,61,43}");
        parser.parseInput("add id/A1234567K gpa/1.0 p/{36,61,43}");
        String input = "stats -avggpa 36";

        // Set up the output stream to capture console output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        // Run the parser
        boolean result = parser.parseInput(input);

        // Assert that the parser continues execution
        assertTrue(result);

        // Verify the expected output
        String expectedOutput = HORIZONTAL_LINE 
                            + "\n" 
                            + "The average GPA for university index 36 (The University of Hong Kong) is: 3.00" 
                            + "\n" 
                            + HORIZONTAL_LINE;
        assertEquals(expectedOutput,outContent.toString().trim());

        // Reset the original System.out
        System.setOut(originalOut);
    }

    @Test
    public void testStatMinCommand() {
        // Simulate user input for 'stats -avggpa' command
        parser.parseInput("add id/A1234567I gpa/5.0 p/{36,61,43}");
        parser.parseInput("add id/A1234567J gpa/3.0 p/{36,61,43}");
        parser.parseInput("add id/A1234567K gpa/1.0 p/{36,61,43}");
        String input = "stats -mingpa 36";

        // Set up the output stream to capture console output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        // Run the parser
        boolean result = parser.parseInput(input);

        // Assert that the parser continues execution
        assertTrue(result);

        // Verify the expected output
        String expectedOutput = HORIZONTAL_LINE 
                            + "\n" 
                            + "The minimum GPA for university index 36 (The University of Hong Kong) is: 1.00" 
                            + "\n" 
                            + HORIZONTAL_LINE;
        assertEquals(expectedOutput,outContent.toString().trim());

        // Reset the original System.out
        System.setOut(originalOut);
    }

    @Test
    public void testViewQuotaCommand() {
        // Simulate user input for 'viewQuota' command
        String input = "viewQuota 36";

        // Set up the output stream to capture console output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        // Run the parser
        boolean result = parser.parseInput(input);

        // Assert that the parser continues execution
        assertTrue(result);

        // Verify the expected output
        String expectedOutput = HORIZONTAL_LINE 
                            + "\n" 
                            + " Index: 36" 
                            + "\n" 
                            + " Name:  The University of Hong Kong"
                            + "\n"
                            + " Quota: 2"
                            + "\n"
                            + HORIZONTAL_LINE;
        assertEquals(expectedOutput,outContent.toString().trim());

        // Reset the original System.out
        System.setOut(originalOut);
    }

    @Test
    public void testRevertCommand() {
        // Simulate user input for allocating a list of students
        parser.parseInput("add id/A1234567I gpa/5.0 p/{36,61,43}");
        parser.parseInput("add id/A2468101J gpa/4.9 p/{32,50,8}");
        parser.parseInput("add id/A3691215K gpa/4.8 p/{29,61,17}");
        parser.parseInput("allocate");

        // Simulate user input for 'revert' command
        String input = "revert";

        // Set up the output stream to capture console output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        // Run the parser
        boolean result = parser.parseInput(input);

        // Assert that the parser continues execution
        assertTrue(result);

        // Verify that student list changes to original state
        for (Student student : studentList.getList()) {
            assertFalse(student.getSuccessfullyAllocated());
            assertEquals(-1, student.getAllocatedUniversity());
        }

        // Verify expected output
        String expectedOutput = HORIZONTAL_LINE + "\n" + Messages.REVERT_COMPLETE + "\n" + HORIZONTAL_LINE;
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
