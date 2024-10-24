package exceptions;

public class SEPIOException extends SEPException {
    public SEPIOException(String message) {
        super(message);
    }

    // Exception for missing "students" node or invalid array
    public static SEPIOException missingStudentsArray() {
        return new SEPIOException("Invalid JSON format! The 'students' array is missing or not an array.");
    }

    // Exception for missing required fields in the student entry
    public static SEPIOException missingRequiredFields() {
        return new SEPIOException("Invalid JSON format! Each student entry must contain 'ID', 'GPA', " +
                "and 'PREFERENCES'.");
    }

    // Exception for invalid data format after checking the values
    public static SEPIOException invalidDataFormat() {
        return new SEPIOException("Invalid data! One or more fields contain invalid data. Please check ID, GPA, " +
                "and PREFERENCES.");
    }

    // Existing CSV and TXT exceptions
    public static SEPIOException rejectCSVFile() {
        return new SEPIOException("Wrong CSV format! Please ensure first row is ID, GPA, PREFERENCES" +
                " and subsequent rows to be <ID>, <GPA>, \"<PREFERENCES>\"\nALL SEPARATED BY A COMMA! " +
                "For e.g. A1234567J, 4.5, \"{1,2,3}\"");
    }

    public static SEPIOException rejectJSONFile() {
        return new SEPIOException("Wrong JSON format! Please ensure your JSON file is formatted with the following.");
    }

    public static SEPIOException rejectTXTFile() {
        return new SEPIOException("Wrong TXT format! Please ensure each row is written as " +
                "id/<ID> gpa/<GPA> p/<PREFERENCES>. For e.g. id/A1234567J gpa/4.5 p/{1,2,3}");
    }
}
