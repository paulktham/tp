package command;

import studentlist.StudentList;

public class ListCommand extends Command {
    public ListCommand(StudentList studentList) {
        super(studentList);
    }

    @Override
    public void run() {
        super.studentList.printStudentList();
    }
}
