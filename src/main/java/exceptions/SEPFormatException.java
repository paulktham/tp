package exceptions;

public class SEPFormatException extends SEPException {
    public SEPFormatException(String message) {
        super(message);
    }

    public static SEPFormatException rejectAddStudentFormat() {
        return new SEPFormatException("Missing input parameters. Please use: "
                + "add id/<Id> gpa/<GPA> p/{<Preference Rankings>}");
    }

    public static SEPFormatException rejectGpaFormat() {
        return new SEPFormatException("Invalid gpa format. Please enter a valid float.");
    }

    public static SEPFormatException rejectPreferenceFormat() {
        return new SEPFormatException("Invalid preference format. "
                + "Please enter a valid integer with curly braces enclosing it.");
    }

    public static SEPFormatException rejectIdFormat() {
        return new SEPFormatException("Invalid ID format. Please enter two uppercase alphabets "
                + "with 7 natural numbers between it.");
    }
}
