package command;

import studentlist.StudentList;
import ui.UI;

public class RevertCommand extends Command {
    private UI ui;

    public RevertCommand(StudentList studentList, UI ui) {
        super(studentList);
        this.ui = ui;
    }

    @Override
    public void run() {
        ui.printResponse("");
    }
}
