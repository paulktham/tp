package Command;

import ui.UI;

public class ExitCommand extends Command {
    public ExitCommand(UI ui) {
        super(ui);
    }

    @Override
    public void run() {
        super.ui.printBye();
    }
}
