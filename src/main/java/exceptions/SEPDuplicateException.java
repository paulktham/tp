package exceptions;

public class SEPDuplicateException extends SEPException {
    public SEPDuplicateException(String message) {
        super(message);
    }

    public static SEPDuplicateException rejectDuplicateStudent() {
        return new SEPDuplicateException("Student is already inside the student list.");
    }
}
