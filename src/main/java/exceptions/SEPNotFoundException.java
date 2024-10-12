package exceptions;

public class SEPNotFoundException extends SEPException {
    public SEPNotFoundException(String message) {
        super(message);
    }

    public static SEPNotFoundException rejectStudentNotFound() {
        return new SEPNotFoundException("Student not found.");
    }
}
