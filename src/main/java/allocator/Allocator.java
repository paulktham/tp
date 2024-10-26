package allocator;

import student.Student;
import studentlist.StudentList;
import university.University;
import university.UniversityRepository;

import java.util.Set;

import exceptions.SEPException;

import java.util.HashSet;

/**
 * The Allocator class manages the allocation of students to universities based on their GPA
 * and university preferences. It provides functionality to set a minimum GPA requirement and
 * to perform the allocation process.
 */

public class Allocator {
    
    private static double minimumGPA = 0;
    private StudentList studentList;
    

    /**
     * Constructs an Allocator with a deep copy of the provided StudentList.
     * This ensures the original list is not modified during allocation.
     * 
     * @param studentList The list of students to be allocated.
     */
    public Allocator(StudentList studentList) {
        this.studentList = new StudentList(studentList);  // don't want to modify the original list
    }

    /**
     * Sets the minimum GPA by processing the user's input.
     * This method uses a helper function found in studentList to validate the GPA.
     * The helper function validateGPA checks if the GPA is valid, meaning it falls within
     * the acceptable range of 0.0 to 5.0. If valid, the GPA is returned and used to set
     * the minimumGPA variable.
     * 
     * @param input The input written by the user to be processed.
     * @throws SEPException if there are any errors in the input.
     */
    public void setMinimumGPA(String input) throws SEPException {
        Set<String> errorMessages = new HashSet<>();
        String[] parts = input.split(" ");
        String stringGpa = parts[1];
        float gpa = studentList.validateGpa(stringGpa, errorMessages);

        if (!errorMessages.isEmpty()) {
            throw new SEPException(String.join("\n", errorMessages));
        }

        // Setting the static minimum GPA criteria to the input
        minimumGPA = gpa;
    }

    /**
     * Returns the current minimum GPA requirement for allocation.
     * 
     * @return The minimum GPA as a double.
     */

    public double getMinimumGPA(){
        return minimumGPA;
    }

    /**
     * Allocates students to universities based on their GPA and preferences.
     * This method sorts students by GPA in descending order and iterates through each student's
     * university preferences. For each student, it checks if a university has available spots 
     * and if the student meets the minimum GPA requirement. If both conditions are met, the student 
     * is allocated to that university, and the allocation process continues with the next student. 
     * Finally, students are re-sorted by ID before the list is returned.
     * 
     * @return the updated StudentList after allocation with each student's assigned university.
     */

    public StudentList allocate() {
        studentList.sortStudentsByGPA();
        for (Student student : studentList.getList()) {
            for (int uni : student.getUniPreferences()) {
                University university = UniversityRepository.getUniversityByIndex(uni);
                if (university.getSpotsLeft() > 0 && student.getGpa() >= minimumGPA) {
                    university.removeASpot();
                    student.setAllocatedUniversity(uni);
                    student.setSuccessfullyAllocated(true);
                    break;
                }
            }
        }
        studentList.sortStudentsById();
        return studentList;
    }
}
