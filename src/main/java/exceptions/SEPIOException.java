package exceptions;

public class SEPIOException extends SEPException {
    public SEPIOException(String message) {
        super(message);
    }

    public static SEPIOException rejectCSVFile() {
        return new SEPIOException("Wrong CSV format! Please ensure first row is ID, GPA, PREFERENCES" +
                " and subsequent rows to be <ID>, <GPA>, \"<PREFERENCES>\". " +
                "For e.g. A1234567J, 4.5, \"{1,2,3}\"");
    }

    public static SEPIOException rejectJSONFile() {
        return new SEPIOException("Wrong JSON format! Please ensure your JSON file is formatted with one " +
                "main key \"students\". The value should be an array of JSON objects containing key-value" +
                " pairs for ID, GPA and PREFERENCES.");
    }

    public static SEPIOException rejectTXTFile() {
        return new SEPIOException("Wrong TXT format! Please ensure each row is written as " +
                "id/<ID> gpa/<GPA> p/<PREFERENCES. For e.g. id/A1234567J gpa/4.5 p/{1,2,3}");
    }
}
