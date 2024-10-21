package allocator;

import student.Student;
import studentlist.StudentList;
import university.University;
import university.UniversityRepository;

import java.util.Set;

import exceptions.SEPException;

import java.util.HashSet;

public class Allocator {
    
    private static double minimumGPA = 0;
    private StudentList studentList;
    

    public Allocator(StudentList studentList) {
        this.studentList = new StudentList(studentList);  // don't want to modify the original list
    }

    public void setMinimumGPA(String stringGpa) throws SEPException{
        Set<String> errorMessages = new HashSet<>();

        float gpa = studentList.validateGpa(stringGpa, errorMessages);

        if (!errorMessages.isEmpty()) {
            throw new SEPException(String.join("\n", errorMessages));
        }

        //Setting the static minimum GPA criteria to the input
        minimumGPA = gpa;
    }

    public double getMinimumGPA(){
        return minimumGPA;
    }
    
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
