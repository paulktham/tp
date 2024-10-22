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

    public static SEPNotFoundException rejectFileNotFound() {
        return new SEPNotFoundException("File do not exist.");
    }
}
