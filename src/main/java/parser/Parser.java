package parser;

import command.AddCommand;
import command.AllocateCommand;
import command.DeleteCommand;
import command.ExitCommand;
import command.GenerateCommand;
import command.HelpCommand;
import command.ListCommand;
import command.StatCommand;
import command.UnknownCommand;
import command.ViewQuotaCommand;
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
    public boolean parseInput(String input) {
        String[] parts = input.split(" ");
        switch (parts[0].toLowerCase()) {
        case "add":
            new AddCommand(this.studentList, input, this.ui).run();
            break;
        case "delete":
            new DeleteCommand(this.studentList, input, this.ui).run();
            break;
        case "list":
            new ListCommand(this.studentList).run();
            break;
        case "allocate":
            new AllocateCommand(this.studentList, this.ui).run();
            break;
        case "exit":
            new ExitCommand(this.studentList,this.ui).run();
            return false;
        case "help":
            new HelpCommand(this.studentList,this.ui).run();
            break;
        case "generate":
            new GenerateCommand(this.studentList, this.ui).run();
            break;
        case "stats":
            new StatCommand(this.studentList, input, this.ui).run();
            break;
        case "viewquota":
            new ViewQuotaCommand(this.studentList, input, this.ui).run();
            break;
        default:
            new UnknownCommand(this.studentList, this.ui).run();
            break;
        }
        return true;
    }
}
