package studentlist;

import exceptions.SEPException;
import exceptions.SEPRangeException;
import exceptions.SEPNotFoundException;
import exceptions.SEPFormatException;
import exceptions.SEPDuplicateException;

import student.Student;
import ui.UI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.HashSet;
import java.util.regex.Pattern;

public class StudentList {
    private ArrayList<Student> students;
    private UI ui;

    public StudentList(UI ui) {
        this.students = new ArrayList<>();
        this.ui = ui;
    }

    // Copy constructor
    public StudentList(StudentList other) {
        this.students = new ArrayList<>(other.students.size());
        for (Student student : other.students) {
            this.students.add(new Student(student));
        }
        this.ui = other.ui;
    }

    public ArrayList<Student> getList() {
        return students;
    }

    public void addStudent(Student student) throws SEPDuplicateException {
        // Check if a student with the same ID already exists
        for (Student existingStudent : students) {
            if (existingStudent.getId().equals(student.getId())) {
                throw SEPDuplicateException.rejectDuplicateStudent();
            }
        }
        students.add(student);
    }

    public void deleteStudent(String id) throws SEPException{
        String[] parts = id.split("delete|id/", 3);
        String studentId = organiseId(parts[2]);
        Pattern idPattern = Pattern.compile("^[A-Z]\\d{7}[A-Z]$");
        if (!idPattern.matcher(studentId).matches()) {
            throw SEPFormatException.rejectIdFormat();
        }
        boolean isRemoved = students.removeIf(student -> student.getId().equals(studentId));
        if (!isRemoved) {
            throw SEPNotFoundException.rejectStudentNotFound();
        }
    }

    // Method to sort students by GPA
    public void sortStudentsByGPA() {
        Collections.sort(students, new Comparator<Student>() {
            @Override
            public int compare(Student s1, Student s2) {
                return Float.compare(s2.getGpa(), s1.getGpa());
            }
        });
    }

    // Method to sort students by ID
    public void sortStudentsById() {
        Collections.sort(students, new Comparator<Student>() {
            @Override
            public int compare(Student s1, Student s2) {
                return s1.getId().compareTo(s2.getId());
            }
        });
    }

    public int getNumStudents() {
        return students.size();
    }

    /**
     * Handles the creation of a Student object by validating the input for Student ID, GPA, and preferences.
     *
     * @param input The input string containing student information in the format "id/{studentId} gpa/{gpa} p/{preferences}".
     * @return The created Student object if all validations pass.
     * @throws SEPException If any validation errors occur, a SEPException containing all error messages is thrown.
     */
    public Student makeStudent(String input) throws SEPException {
        Set<String> errorMessages = new HashSet<>();

        String[] parts = splitInput(input);

        String studentId = organiseId(parts[1]);
        validateStudentId(studentId, errorMessages);

        // Validate GPA
        float gpa = validateGpa(parts[2].trim(), errorMessages);

        // Validate Preferences
        ArrayList<Integer> preferences = validatePreferences(parts[3].trim(), errorMessages);

        // If any errors occurred, throw a single exception with all error messages
        if (!errorMessages.isEmpty()) {
            throw new SEPException(String.join("\n", errorMessages));
        }

        // Create and return the Student object
        return new Student(studentId, gpa, preferences);
    }

    public void printStudentList() {
        try {
            ui.printStudentList(this.students);
        } catch (SEPException e) {
            ui.printResponse(e.getMessage());
        }
    }

    public void generateReport() {
        ui.generateReport(students);
    }

    // Setter method to update the student list
    public void setStudentList(StudentList studentList) {
        this.students = studentList.students; // Overwrite the current ArrayList
    }

    private String organiseId(String id) {
        String studentId = id.trim();
        // Remove all spaces from the studentId before validating
        return studentId.replaceAll("\\s+", "");
    }
    // Helper method for splitting the input
    private String[] splitInput(String input) throws SEPException {
        String[] parts = input.split("id/|gpa/|p/");
        if (parts.length != 4) {
            throw SEPFormatException.rejectAddStudentFormat();
        }
        return parts;
    }

    // Helper method for validating Student ID
    private void validateStudentId(String studentId, Set<String> errorMessages) {
        Pattern idPattern = Pattern.compile("^[A-Z]\\d{7}[A-Z]$");
        if (!idPattern.matcher(studentId).matches()) {
            errorMessages.add(SEPFormatException.rejectIdFormat().getMessage());
        }
    }

    // Helper method for validating GPA
    private float validateGpa(String gpaStr, Set<String> errorMessages) {
        float gpa = 0.0f;
        try {
            gpa = Float.parseFloat(gpaStr);
            if (gpa < 0.0 || gpa > 5.0) {
                errorMessages.add(SEPRangeException.rejectGpaRange().getMessage());
            }
        } catch (NumberFormatException e) {
            errorMessages.add(SEPFormatException.rejectGpaFormat().getMessage());
        }
        return gpa;
    }

    // Helper method for validating Preferences
    private ArrayList<Integer> validatePreferences(String preferencesData, Set<String> errorMessages) {
        ArrayList<Integer> preferences = new ArrayList<>();
        String[] numberStrings = preferencesData.replaceAll("[{}]", "").split(",");

        if (numberStrings.length > 3 || numberStrings[0].trim().isEmpty()) {
            errorMessages.add(SEPRangeException.rejectRankingsRange().getMessage());
        }

        for (String numberString : numberStrings) {
            try {
                int preference = Integer.parseInt(numberString.trim());
                if (preference < 1 || preference > 92) {
                    errorMessages.add(SEPRangeException.rejectPreferenceRange().getMessage());
                } else {
                    preferences.add(preference);
                }
            } catch (NumberFormatException e) {
                errorMessages.add(SEPFormatException.rejectPreferenceFormat().getMessage());
            }
        }

        return preferences;
    }
}
