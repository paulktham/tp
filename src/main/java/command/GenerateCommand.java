package command;

import studentlist.StudentList;

public class GenerateCommand extends Command {
    public GenerateCommand(StudentList studentList) {
        super(studentList);
    }

    @Override
    public void run() {
        super.studentList.generateReport();
    }
}
