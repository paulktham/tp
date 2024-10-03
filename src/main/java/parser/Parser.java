package parser;

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
            // new AddCommand();
            break;
        case "delete":
            // new DeleteCommand();
            break;
        case "list":
            // new ListCommand();
            break;
        case "allocate":
            // new AllocateCommand();
            break;
        case "exit":
            this.ui.printBye();
            return false;
        case "help":
            // new HelpCommand();
            break;
        case "generate":
            // new GenerateCommand();
            break;
        default:
            this.ui.println("Invalid command");
            break;
        }
        return true;
    }
}
