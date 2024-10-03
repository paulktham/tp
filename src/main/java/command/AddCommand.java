package command;

import studentlist.StudentList;

public class AddCommand extends Command {
    private String input;

    public AddCommand(StudentList studentList, String input) {
        super(studentList);
        this.input = input;
    }

    @Override
    public void run() {
        try {
            super.studentList.addStudent(super.studentList.makeStudent(this.input));
        } catch (Exception e) {
            System.out.println(e.getMessage());  // Temp @Holy-An
        }
    }
}
