package Command;

import ui.UI;

public class ListCommand extends Command {
    public ListCommand(UI ui) {
        super(ui);
    }

    @Override
    public void run() {
        super.ui.printStudentList();
    }
}
