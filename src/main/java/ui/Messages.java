package ui;

public enum Messages {
    WELCOME("Hi! Welcome to FindOurSEP!"),
    EXIT("Goodbye! Have a great day!"),
    ALLOCATE_COMPLETE("Allocation algorithm complete! Run generate command to view."),
    HELP("""
        Here is the list of possible commands:
            add         Adds a student with the specified ID, GPA, and preference rankings.
                        Example: add id/A1234567I gpa/5.0 p/{13,61,43}
                        Note: PREFERENCE_RANKINGS should be enclosed in curly braces

            delete      Deletes the student with the specified ID from the list.
                        Example: delete A1234567I

            list        Lists all students currently in the system.

            allocate    Allocates students to available slots based on their preferences.

            generate    Generates a report of allocations and student data.

            exit        Exits the application.

            help        Displays this help message.
        """),
    ERROR("An error occurred. Please try again.");

    private final String message;

    Messages(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return this.message;
    }
}
