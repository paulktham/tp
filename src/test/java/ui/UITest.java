package ui;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import exceptions.SEPEmptyException;
import student.Student;

public class UITest {
    private UI ui;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setUp() {
        // Redirect system output to capture it for tests
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Initialize UI with default constructor
        ui = new UI();
    }

    @Test
    void test_printResponse() {
        String message = "Hello, World!";
        ui.printResponse(message);
        String expected = "-".repeat(80) + "\n" + message + "\n" + "-".repeat(80) + "\n";
        assertEquals(expected.trim(), outContent.toString().trim());
    }

    @Test
    void test_printResponse_null() {
        ui.printResponse(null);
        String expected = "-".repeat(80) + "\nnull\n" + "-".repeat(80) + "\n";
        assertEquals(expected.trim(), outContent.toString().trim());
    }

    @Test
    void test_getUserInput_valid() {
        // Simulate user input
        String simulatedInput = "test input\n";
        ByteArrayInputStream inContent = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inContent);

        UI testUi = new UI();
        assertEquals("test input", testUi.getUserInput());
    }

    @Test
    void test_getUserInput_error() {
        // Simulate erroneous input
        ByteArrayInputStream inContent = new ByteArrayInputStream(new byte[0]);
        System.setIn(inContent);

        assertEquals("", ui.getUserInput());
        assertTrue(outContent.toString().contains("Error reading input"));
    }

    @Test
    void test_sayHi() {
        ui.sayHi();
        assertTrue(outContent.toString().contains(Messages.WELCOME.toString()));
    }

    @Test
    void test_cleanupAndExit() {
        ui.cleanupAndExit();
        assertTrue(outContent.toString().contains("Adios, amigo!"));
    }

    @Test
    void test_printStudentList_valid() throws SEPEmptyException {
        ArrayList<Student> studentList = new ArrayList<>();
        studentList.add(new Student("A1234567I", 3.9f, new ArrayList<>(List.of(1, 2))));
        studentList.add(new Student("A2234567J", 3.5f, new ArrayList<>(List.of(3, 1))));

        ui.printStudentList(studentList);

        System.out.println(outContent.toString());

        String expectedTable = """
            ┌───────────────┬─────────┬─────────────────────────┐
            │    Student    │   GPA   │   Preference Rankings   │
            ├───────────────┼─────────┼─────────────────────────┤
            │   A1234567I   │   3.9   │           1,2           │
            │   A2234567J   │   3.5   │           3,1           │
            └───────────────┴─────────┴─────────────────────────┘
            """;
        System.out.println(expectedTable);

        assertTrue(outContent.toString().contains(expectedTable));
    }

    @Test
    void test_printStudentList_empty() {
        ArrayList<Student> studentList = new ArrayList<>();
        assertThrows(SEPEmptyException.class, () -> ui.printStudentList(studentList));
    }

    @Test
    void testGenerateReport_valid() throws SEPEmptyException {
        // Create a list of students for the report
        ArrayList<Student> studentList = new ArrayList<>();
        studentList.add(new Student("A1234567I", 3.9f, new ArrayList<>(List.of(1, 2))));
        studentList.add(new Student("A2234567J", 3.5f, new ArrayList<>(List.of(3, 1))));

        ui.generateReport(studentList);

        String expectedReport = """
            ┌───────────────┬─────────┬─────────────────────────┐
            │    Student    │   GPA   │   Preference Rankings   │
            ├───────────────┼─────────┼─────────────────────────┤
            │   A1234567I   │   3.9   │           1,2           │
            │   A2234567J   │   3.5   │           3,1           │
            └───────────────┴─────────┴─────────────────────────┘
            """;
        System.out.println(expectedReport);

        assertTrue(outContent.toString().contains(expectedReport));
    }

    @Test
    void test_generateReport_empty() {
        ArrayList<Student> studentList = new ArrayList<>();
        assertThrows(SEPEmptyException.class, () -> ui.generateReport(studentList));
    }

    @Test
    void test_printAllocatingMessage() throws InterruptedException {
        Thread allocationThread = new Thread(() -> ui.printAllocatingMessage());
        allocationThread.start();

        Thread.sleep(2000); // Let the loading message print for a while
        allocationThread.interrupt(); // Interrupt the thread to stop the loading

        allocationThread.join(); // Ensure the thread has finished

        assertTrue(outContent.toString().contains(Messages.ALLOCATE_COMPLETE.toString()));
    }
}
