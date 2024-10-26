package command;

import studentlist.StudentList;
import ui.Messages;
import ui.UI;

/**
 * Command to revert the allocation status of all students in the StudentList.
 * Resets each student's allocation status and associated university index
 * to indicate no allocation, allowing for a fresh allocation process.
 */
public class RevertCommand extends Command {
    /**
     * Reference to the UI object used for displaying messages to the user.
     */
    private UI ui;

    /**
     * Constructs a RevertCommand with the specified student list and user interface.
     *
     * @param studentList The reference to the StudentList object.
     * @param ui          The reference to the UI object.
     */
    public RevertCommand(StudentList studentList, UI ui) {
        super(studentList);
        this.ui = ui;
    }

    /**
     * Executes the revert command, resetting the allocation status of all students
     * in the list to their unallocated state. Upon completion, a confirmation message
     * is displayed to the user.
     */
    @Override
    public void run() {
        studentList.revertAllocation();
        ui.printResponse(Messages.REVERT_COMPLETE);
    }
}
