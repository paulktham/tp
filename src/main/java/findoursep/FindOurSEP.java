package findoursep;

import studentlist.StudentList;
import ui.UI;
import parser.Parser;

public class FindOurSEP {
    private UI ui;
    private Parser parser;
    private StudentList studentList;

    /**
     * Constructs a new FindOurSEP object.
     * This constructor initializes the UI and parser.
     */
    public FindOurSEP() {
        this.ui = new UI();
        this.studentList = new StudentList(this.ui);
        this.parser = new Parser(this.studentList,this.ui);
    }
    
    /**
     * Main entry-point for the findoursep.FindOurSEP application.
     */
    public static void main(String[] args) {
        FindOurSEP bob = new FindOurSEP();
        bob.ui.sayHi();
        boolean isRunning = true;
        while (isRunning) {
            isRunning = bob.parser.parseInput();
        }
    }
}
