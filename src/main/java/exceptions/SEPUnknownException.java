package exceptions;

public class SEPUnknownException extends SEPException {
    public SEPUnknownException(String message) {
        super(message);
    }

    public static SEPEmptyException rejectUnknownFileType() {
        return new SEPEmptyException("Unknown/Unsupported file type detected!");
    }
}
