package allocator;

import student.Student;
import studentlist.StudentList;
import university.University;
import university.UniversityRepository;

public class Allocator {
    
    private StudentList studentList;
    private UniversityRepository universityList;

    public Allocator(StudentList studentList, UniversityRepository universityList) {
        this.studentList = new StudentList(studentList);  // don't want to modify the original list
        this.universityList = universityList;
    }
    
    public StudentList allocate() {
        studentList.sortStudentsByGPA();
        for (Student student : studentList.getList()) {
            for (int uni : student.getUniPreferences()) {
                University university = universityList.getUniversityByIndex(uni);
                if (university.getSpotsLeft() > 0) {
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

    // unit test
    public void main() {

    }
}
