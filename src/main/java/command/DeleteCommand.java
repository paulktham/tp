package command;

import exceptions.SEPException;
import studentlist.StudentList;
import ui.UI;

public class DeleteCommand extends Command {
    private String input;
    private UI ui;

    public DeleteCommand(StudentList studentList, String input, UI ui) {
        super(studentList);
        this.input = input;
        this.ui = ui;
    }

    @Override
    public void run() {
        try {
            super.studentList.deleteStudent(this.input);
            ui.printResponse("Removed student, " + studentList.getNumStudents() + " student(s) left");
        } catch (SEPException e) {
            ui.printResponse(e.getMessage());
        }

    }
}
