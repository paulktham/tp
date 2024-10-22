package findoursep;

import storage.Storage;
import studentlist.StudentList;
import ui.UI;
import parser.Parser;

import java.util.ArrayList;

public class FindOurSEP {
    private UI ui;
    private Parser parser;
    private StudentList studentList;
    private Storage storage;

    /**
     * Constructs a new FindOurSEP object.
     * This constructor initializes the UI and parser.
     */
    public FindOurSEP() {
        this.ui = new UI();
        this.studentList = new StudentList(this.ui);
        this.parser = new Parser(this.studentList,this.ui);
    }

    public void setUpStorage() {
        this.ui.printConfigMessage();
        String input = this.ui.getUserInput();
        String filePath = "";
        if (input.trim().equals("2")) {
            ui.promptFilePath();
            filePath = this.ui.getUserInput();
        } else {
            ui.printResponse("Let's begin!");
        }
        this.storage = new Storage(filePath);
    }

    /**
     * The main loop of the application.
     * This method will continue to loop until the user chooses to exit the
     * application. It will read the user's input, process it.
     */
    public void start() {
        String line;
        boolean isRunning = true;
        while (isRunning) {
            line = this.ui.getUserInput();
            isRunning = this.parser.parseInput(line);
        }
    }

    /**
     * Main entry-point for the findoursep.FindOurSEP application.
     */
    public static void main(String[] args) {
        //FindOurSEP bob = new FindOurSEP();
        //bob.start();
        Storage g = new Storage("test.csv");
        try {
            ArrayList<String> students = g.processFile();
            for (String student : students) {
                System.out.println(student);
            }
        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());
        }
    }
}
