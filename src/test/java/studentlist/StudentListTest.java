package studentlist;

import exceptions.SEPDuplicateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import student.Student;
import ui.UI;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class StudentListTest {
    private StudentList studentList;
    private ArrayList<Integer> uniPreferences = new ArrayList<>(Arrays.asList(1,2,3));
    private Student bob = new Student("A1234567K", (float)4.6, uniPreferences);

    @BeforeEach
    public void setUp() {
        this.studentList = new StudentList(new UI());
    }

    @Test
    public void addStudent_duplicateStudent_exceptionThrown() {
        assertDoesNotThrow(() -> this.studentList.addStudent(this.bob));
        assertThrows(SEPDuplicateException.class, () -> this.studentList.addStudent(this.bob));
    }

    @Test
    public void testAddStudent() {
        assertDoesNotThrow(() -> this.studentList.addStudent(this.bob));

        assertEquals(1, this.studentList.getNumStudents());

        assertEquals("A1234567K", studentList.getList().get(0).getId());
    }

    @Test
    public void testDeleteStudent() {
        assertDoesNotThrow(() -> this.studentList.addStudent(this.bob));
        assertEquals(1, studentList.getNumStudents());

        assertDoesNotThrow(() -> this.studentList.deleteStudent("delete A1234567K"));
        assertEquals(0, studentList.getNumStudents());
    }

    @Test
    public void testSortStudentsByGPA() {
        Student student1 = new Student("A1234567B", 3.2f, new ArrayList<>());
        Student student2 = new Student("A2345678C", 4.8f, new ArrayList<>());
        Student student3 = new Student("A3456789D", 3.9f, new ArrayList<>());

        assertDoesNotThrow(() -> this.studentList.addStudent(student1));
        assertDoesNotThrow(() -> this.studentList.addStudent(student2));
        assertDoesNotThrow(() -> this.studentList.addStudent(student3));

        studentList.sortStudentsByGPA();

        assertEquals("A2345678C", studentList.getList().get(0).getId()); // Highest GPA first
        assertEquals("A1234567B", studentList.getList().get(2).getId()); // Lowest GPA last
    }

    @Test
    public void testSortStudentsById() {
        Student student1 = new Student("A1234567B", 3.2f, new ArrayList<>());
        Student student2 = new Student("A2345678C", 4.8f, new ArrayList<>());
        Student student3 = new Student("A3456789D", 3.9f, new ArrayList<>());

        assertDoesNotThrow(() -> this.studentList.addStudent(student1));
        assertDoesNotThrow(() -> this.studentList.addStudent(student2));
        assertDoesNotThrow(() -> this.studentList.addStudent(student3));

        studentList.sortStudentsById();

        assertEquals("A1234567B", studentList.getList().get(0).getId()); // Lexicographical order
        assertEquals("A3456789D", studentList.getList().get(2).getId());
    }

    @Test
    public void testMakeStudent() {
        String input = "add id/A1234567B gpa/4.5 p/{1,2,3}";
        Student student = assertDoesNotThrow(() -> this.studentList.makeStudent(input));

        assertNotNull(student);
        assertEquals("A1234567B", student.getId());
        assertEquals(4.5f, student.getGpa());
        assertEquals(3, student.getUniPreferences().size());
    }
}
