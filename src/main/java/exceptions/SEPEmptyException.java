package exceptions;

/**
 * This class handles exceptions when output is empty.
 */
public class SEPEmptyException extends SEPException {
    public SEPEmptyException(String message) {
        super(message);
    }

    public static SEPEmptyException rejectEmptyStudentList() {
        return new SEPEmptyException("No students available for generation.");
    }
}
