package Command;

import ui.UI;

public class AddCommand extends Command {
    public AddCommand(UI ui) {
        super(ui);
    }

    @Override
    public void run() {
        super.ui.addStudent();
    }
}
