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
    public static SEPUnknownException rejectUnknownFileType() {
        return new SEPUnknownException("Unknown/Unsupported file type detected!");
    }
}
