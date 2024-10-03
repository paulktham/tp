package studentlist;

import student.Student;
import ui.UI;

import java.util.ArrayList;

public class StudentList {
    private ArrayList<Student> students;
    private UI ui;

    public StudentList(UI ui) {
        this.students = new ArrayList<>();
        this.ui = ui;
    }

    public int getNumStudents() {
        return students.size();
    }

    public Student makeStudent(String input) throws Exception {
        // Split based on id/, gpa/, and p/ without spaces
        String[] parts = input.split("id/|gpa/|p/");

        if (parts.length != 4) {
            throw new Exception("Wrong input format");
        }

        // Trim spaces and braces, then split the preferences
        System.out.println(parts[3]);
        String preferencesData = parts[3].trim().replaceAll("[{}]", "");
        String[] numberStrings = preferencesData.split(",");
        System.out.println(preferencesData);

        ArrayList<Integer> preferences = new ArrayList<>();
        for (String numberString : numberStrings) {
            preferences.add(Integer.parseInt(numberString.trim()));
        }

        // Create and return the Student object
        return new Student(parts[1].trim(), Float.parseFloat(parts[2].trim()), preferences);
    }


    public void addStudent(Student student) {
        students.add(student);
        System.out.println(getNumStudents() + " students remaining");
    }

    public void deleteStudent(String id) {
        String[] parts = id.split(" ");
        students.removeIf(student -> student.getId().equals(parts[1]));
        System.out.println(getNumStudents() + " students remaining");
    }

    public void printStudentList() {
        ui.printStudentList(this.students);
    }

    public void generateReport() {
        ui.generateReport(students);
    }
}
