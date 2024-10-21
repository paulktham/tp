package command;

import exceptions.SEPException;
import exceptions.SEPFormatException;
import exceptions.SEPNotFoundException;

import studentlist.StudentList;
import ui.UI;
import university.University;
import university.UniversityRepository;

public class ViewQuotaCommand extends Command {
    private String input;
    private UI ui;

    public ViewQuotaCommand(StudentList studentList, String input, UI ui) {
        super(studentList);
        this.input = input;
        this.ui = ui;
    }

    @Override
    public void run() {
        try {
            String[] parts = input.split(" ");
            if (parts.length != 2) {
                throw SEPFormatException.rejectViewQuotaFormat();
            }

            String uniIndeString = parts[1].trim();
            if (!uniIndeString.matches("-?\\d+")) {
                throw SEPFormatException.rejectViewQuotaFormat();
            }
            
            int uniIndex = Integer.parseInt(uniIndeString);
            University university = UniversityRepository.getUniversityByIndex(uniIndex);
            if (university == null) {
                throw SEPNotFoundException.rejectUniversityNotFound();
            }

            ui.printResponse(" Index: " + uniIndex + "\n" +
                             " Name:  " + university.getFullName() + "\n" +
                             " Quota: " + university.getSpotsLeft());
        } catch (SEPException e) {
            ui.printResponse(e.getMessage());
        }
    }
}
