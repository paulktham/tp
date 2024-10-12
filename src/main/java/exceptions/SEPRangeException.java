package exceptions;

public class SEPRangeException extends SEPException {
    public SEPRangeException(String message) {
        super(message);
    }

    public static SEPRangeException rejectGpaRange() {
        return new SEPRangeException("GPA should be between 0 and 5.");
    }

    public static SEPRangeException rejectRankingsRange() {
        return new SEPRangeException("Preferences should not contain more than three rankings.");
    }

    public static SEPRangeException rejectPreferenceRange() {
        return new SEPRangeException("Preferences should be between 1 and 92.");
    }
}
