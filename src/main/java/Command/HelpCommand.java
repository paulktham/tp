package Command;

import ui.UI;

public class HelpCommand extends Command {
    public HelpCommand(UI ui) {
        super(ui);
    }

    @Override
    public void run() {
        super.ui.printHelp();
    }
}
