package Command;

import ui.UI;

public class DeleteCommand extends Command {
    public DeleteCommand(UI ui) {
        super(ui);
    }

    @Override
    public void run() {
        super.ui.deleteStudent();
    }
}
