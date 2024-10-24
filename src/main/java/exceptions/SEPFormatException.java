package exceptions;

/**
 * This class handles format exceptions for operations
 * such as adding and deleting a student.
 */
public class SEPFormatException extends SEPException {
    public SEPFormatException(String message) {
        super(message);
    }

    public static SEPFormatException rejectDataFormat(String[] line) {
        return new SEPFormatException(String.join(",", line) + "\" is not in correct format! " +
                "Please ensure that they are separated by a comma.");
    }

    public static SEPFormatException rejectAddStudentFormat() {
        return new SEPFormatException("Invalid add student format. Please use: "
                + "add id/<Id> gpa/<GPA> p/{<Preference Rankings>}");
    }

    public static SEPFormatException rejectGpaFormat() {
        return new SEPFormatException("Invalid gpa format. Please enter a valid float "
                + "with a maximum of 2 decimal places.");
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
