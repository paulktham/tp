package parser;

import command.GenerateCommand;
import command.AddCommand;
import command.AllocateCommand;
import command.DeleteCommand;
import command.ExitCommand;
import command.HelpCommand;
import command.ListCommand;
import studentlist.StudentList;
import ui.UI;

/**
 * The Parser class is the main class responsible for parsing the user's input and executing the corresponding command.
 */
public class Parser {
    private StudentList studentList;
    private UI ui;

    public Parser(StudentList studentList, UI ui) {
        this.studentList = studentList;
        this.ui = ui;
    }

    /**
     * Parses the user's input and execute the corresponding command.
     * 
     * @return false if the user wants to exit the application, true otherwise.
     */
    public boolean parseInput() {
        String input = this.ui.getUserInput();
        String[] parts = input.split(" ");
        switch (parts[0].toLowerCase()) {
        case "add":
            new AddCommand(this.studentList,input).run();
            break;
        case "delete":
            new DeleteCommand(this.studentList,input).run();
            break;
        case "list":
            new ListCommand(this.studentList).run();
            break;
        case "allocate":
            new AllocateCommand(this.studentList).run();
            break;
        case "exit":
            new ExitCommand(this.studentList,this.ui).run();
            return false;
        case "help":
            new HelpCommand(this.studentList,this.ui).run();
            break;
        case "generate":
            new GenerateCommand(this.studentList).run();
            break;
        default:
            this.ui.printResponse("Invalid command");
            break;
        }
        return true;
    }
}
