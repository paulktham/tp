package command;

import allocator.Allocator;
import exceptions.SEPException;
import studentlist.StudentList;
import ui.UI;

public class CriteriaCommand extends Command {
    private String input;
    private UI ui;
    private Allocator allocator;

    public CriteriaCommand (StudentList studentList,String input, UI ui) {
        super(studentList);
        this.input = input;
        this.ui = ui;
        this.allocator = new Allocator(studentList);
    }

    /*
     * Takes the input and sends the second part of the input into the setMinimumGPA function in allocator
     */

    @Override
    public void run() {
        try{
            String[] parts = input.split(" ");
            allocator.setMinimumGPA(parts[1]);
            ui.printResponse("Minimum requirement of GPA set to " + parts[1] + " successfully.");
        } catch (SEPException e) {
            ui.printResponse(e.getMessage());
        }
    }
    
}
