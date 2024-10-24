package findoursep;

import exceptions.SEPException;
import storage.Storage;
import studentlist.StudentList;
import ui.UI;
import parser.Parser;

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

    public boolean isValidOption(String option) {
        return option.equals("1") || option.equals("2");
    }


    public void setUpStorage() {
        this.ui.printConfigMessage();
        String input = this.ui.getUserInput().trim();
        String filePath;
        while (!isValidOption(input)) {
            ui.printResponse("Boss, type 1 or 2 only leh!");
            input = this.ui.getUserInput().trim();
        }
        if (input.equals("2")) {
            ui.promptFilePath();
            filePath = this.ui.getUserInput();
        } else {
            ui.printResponse("Let's begin!");
            return;
        }
        this.storage = new Storage(filePath,this.parser);
        try {
            if (this.storage.processFile()) {
                this.ui.printFileLoadSuccessMessage();
            }
        } catch (SEPException e) {
            this.ui.printResponse(e.getMessage());
            System.exit(0);
        }
    }


    /**
     * The main loop of the application.
     * This method will continue to loop until the user chooses to exit the
     * application. It will read the user's input, process it.
     */
    public void start() {
        this.setUpStorage();
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
        FindOurSEP bob = new FindOurSEP();
        bob.start();
        //bob.setUpStorage();

        /*
        UI ui = new UI();
        StudentList sl = new StudentList(ui);
        Parser p = new Parser(sl,ui);
        Storage s = new Storage("test.txt",p);
        try {
            boolean st = s.processFile();
            if (st) {
                System.out.println("Successfully processed file");
            } else {
                System.out.println("Failed to process file");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        */
    }
}
