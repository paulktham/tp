package exceptions;

public class SEPEmptyException extends SEPException {
    public SEPEmptyException(String message) {
        super(message);
    }

    public static SEPEmptyException rejectEmptyStudentList() {
        return new SEPEmptyException("No students available for report generation.");
    }
}
