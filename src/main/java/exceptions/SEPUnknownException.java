package exceptions;

public class SEPUnknownException extends SEPException {
    public SEPUnknownException(String message) {
        super(message);
    }

    /**
     * Returns a SEPEmptyException with a message indicating that the file type
     * is unknown or unsupported.
     *
     * @return A SEPEmptyException with the appropriate message.
     */
    public static SEPEmptyException rejectUnknownFileType() {
        return new SEPEmptyException("Unknown/Unsupported file type detected!");
    }
}
