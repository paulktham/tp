package command;

import exceptions.SEPException;
import exceptions.SEPFormatException;
import exceptions.SEPNotFoundException;

import student.Student;
import studentlist.StudentList;
import ui.UI;
import university.UniversityRepository;

import java.util.List;
import java.util.stream.Collectors;

public class StatCommand extends Command {
    private String input;
    private UI ui;

    public StatCommand(StudentList studentList, String input, UI ui) {
        super(studentList);
        this.input = input;
        this.ui = ui;
    }

    @Override
    public void run() {
        try {
            String[] parts = input.split(" ");
            if (parts.length != 3) {
                throw SEPFormatException.rejectStatFormat();
            }

            String statType = parts[1].trim().toLowerCase();

            if (!statType.equals("-avggpa") && !statType.equals("-mingpa")) {
                throw SEPFormatException.rejectStatFormat();
            }

            String uniIndeString = parts[2].trim();
            if (!uniIndeString.matches("-?\\d+")) {
                throw SEPFormatException.rejectStatFormat();
            }
            
            int uniIndex = Integer.parseInt(uniIndeString);
            if (UniversityRepository.getUniversityByIndex(uniIndex) == null) {
                throw SEPNotFoundException.rejectUniversityNotFound();
            }

            List<Student> filteredStudents = super.studentList.getList().stream()
                    .filter(student -> student.getUniPreferences().contains(uniIndex))
                    .collect(Collectors.toList());

            if (filteredStudents.isEmpty()) {
                ui.printResponse("No students have chosen this university.");
                return;
            }

            switch (statType) {
            case "-avggpa":
                double avgGpa = filteredStudents.stream()
                        .mapToDouble(Student::getGpa)
                        .average()
                        .orElse(0.0);
                ui.printResponse("The average GPA for university index "  + uniIndex
                                + " (" + UniversityRepository.getUniversityByIndex(uniIndex).getFullName() + ")" 
                                + " is: " + String.format("%.2f", avgGpa));
                break;
            case "-mingpa":
                double minGpa = filteredStudents.stream()
                        .mapToDouble(Student::getGpa)
                        .min()
                        .orElse(0.0);
                ui.printResponse("The minimum GPA for university index " + uniIndex 
                                + " (" + UniversityRepository.getUniversityByIndex(uniIndex).getFullName() + ")"
                                + " is: " + String.format("%.2f", minGpa));
                break;
            default:
                throw SEPFormatException.rejectStatFormat();
            }

        } catch (SEPException e) {
            ui.printResponse(e.getMessage());
        }
    }
}
