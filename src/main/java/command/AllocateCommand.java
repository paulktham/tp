package command;

import studentlist.StudentList;
import ui.UI;

public class AllocateCommand extends Command {
    private UI ui;

    public AllocateCommand(StudentList studentList, UI ui) {
        super(studentList);
        this.ui = ui;
    }

    @Override
    public void run() {
        // call algorithm function, can use the studentlist array as input
        ui.printAllocatingMessage();
    }
}
