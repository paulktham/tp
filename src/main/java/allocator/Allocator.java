package allocator;

import student.Student;
import studentlist.StudentList;
import university.University;
import university.UniversityRepository;

public class Allocator {
    
    private StudentList studentList;

    public Allocator(StudentList studentList) {
        this.studentList = new StudentList(studentList);  // don't want to modify the original list
    }
    
    public StudentList allocate() {
        studentList.sortStudentsByGPA();
        for (Student student : studentList.getList()) {
            for (int uni : student.getUniPreferences()) {
                University university = UniversityRepository.getUniversityByIndex(uni);
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
}
