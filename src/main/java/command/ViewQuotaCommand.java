package command;

import exceptions.SEPEmptyException;
import exceptions.SEPException;
import exceptions.SEPFormatException;

import studentlist.StudentList;
import ui.UI;
import university.University;
import university.UniversityRepository;

/**
 * The ViewQuotaCommand class handles the "viewQuota" command, which displays information
 * about a university's quota based on the specified university index. The information includes
 * the university's name and the number of spots left for allocation.
 */
public class ViewQuotaCommand extends Command {
    private String input;
    private UI ui;

    /**
     * Constructs a new ViewQuotaCommand with the given student list, user input, and UI instance.
     *
     * @param studentList The StudentList containing all student information.
     * @param input The input string provided by the user.
     * @param ui The UI instance for interacting with the user.
     */
    public ViewQuotaCommand(StudentList studentList, String input, UI ui) {
        super(studentList);
        this.input = input;
        this.ui = ui;
    }

    /**
     * Executes the viewQuota command, validating the input format and displaying
     * the university's information, including its index, name, and the remaining quota (spots left).
     *
     * @throws SEPException if there is any issue with the command input or if the university
     *                      index is not found.
     */
    @Override
    public void run() {
        try {
            // Split the input and validate the parts
            String[] parts = input.split(" ");
            if (parts.length != 2) {
                throw SEPFormatException.rejectViewQuotaFormat();
            }

            // Validate the university index format
            String uniIndeString = parts[1].trim();
            if (!uniIndeString.matches("-?\\d+")) {
                throw SEPFormatException.rejectViewQuotaFormat();
            }

            int uniIndex = Integer.parseInt(uniIndeString);

            // Retrieve the university from the repository and check if it exists
            University university = UniversityRepository.getUniversityByIndex(uniIndex);
            if (university == null) {
                throw SEPEmptyException.rejectUniversityNotFound();
            }

            // Display the university's information, including its quota
            ui.printResponse(" Index: " + uniIndex + "\n" +
                             " Name:  " + university.getFullName() + "\n" +
                             " Quota: " + university.getSpotsLeft());
        } catch (SEPException e) {
            ui.printResponse(e.getMessage());
        }
    }
}
