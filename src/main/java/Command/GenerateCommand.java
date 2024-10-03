package Command;

import ui.UI;

public class GenerateCommand extends Command {
    public GenerateCommand(UI ui) {
        super(ui);
    }

    @Override
    public void run() {
        super.ui.generateReport();
    }
}
