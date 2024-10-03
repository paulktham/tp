package command;

import studentlist.StudentList;

public class AllocateCommand extends Command {
    public AllocateCommand(StudentList studentList) {
        super(studentList);
    }

    @Override
    public void run() {
        // call algorithm function, can use the studentlist array as input
    }
}
