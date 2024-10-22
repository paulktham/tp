package exceptions;

public class SEPUnknownException extends SEPException {
    public SEPUnknownException(String message) {
        super(message);
    }

    public static SEPEmptyException rejectUnknownFile() {
        return new SEPEmptyException("Unknown/Unsupported file detected!");
    }
}
