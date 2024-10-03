package parser;

import Command.GenerateCommand;
import Command.AddCommand;
import Command.AllocateCommand;
import Command.DeleteCommand;
import Command.ExitCommand;
import Command.HelpCommand;
import Command.ListCommand;
import ui.UI;

/**
 * The Parser class is the main class responsible for parsing the user's input and executing the corresponding command.
 */
public class Parser {
    private UI ui;

    public Parser(UI ui) {
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
            new AddCommand(this.ui).run();
            break;
        case "delete":
            new DeleteCommand(this.ui).run();
            break;
        case "list":
            new ListCommand(this.ui).run();
            break;
        case "allocate":
            new AllocateCommand(this.ui).run();
            break;
        case "exit":
            new ExitCommand(this.ui).run();
            return false;
        case "help":
            new HelpCommand(this.ui).run();
            break;
        case "generate":
            new GenerateCommand(this.ui).run();
            break;
        default:
            this.ui.println("Invalid command");
            break;
        }
        return true;
    }
}
