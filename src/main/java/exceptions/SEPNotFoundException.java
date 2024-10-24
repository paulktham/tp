package exceptions;

/**
 * This class handles exceptions for operations such as deleting a student.
 */
public class SEPNotFoundException extends SEPException {
    public SEPNotFoundException(String message) {
        super(message);
    }

    public static SEPNotFoundException rejectStudentNotFound() {
        return new SEPNotFoundException("Student not found.");
    }

    public static SEPNotFoundException rejectUniversityNotFound() {
        return new SEPNotFoundException("The specified university was not found. Please check the university index.");
    }

    public static SEPNotFoundException rejectFileNotFound() {
        return new SEPNotFoundException("You sure this file exist anot...Don't make me go " +
                "through your folder for nothing leh :(.\nDon't want talk to you already la BYE");
    }
}
