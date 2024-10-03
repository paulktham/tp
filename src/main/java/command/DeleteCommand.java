package command;

import studentlist.StudentList;

public class DeleteCommand extends Command {
    private String input;

    public DeleteCommand(StudentList studentList, String input) {
        super(studentList);
        this.input = input;
    }

    @Override
    public void run() {
        super.studentList.deleteStudent(this.input);
    }
}
