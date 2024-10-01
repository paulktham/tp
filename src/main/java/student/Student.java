package student;

public class Student {
    private String name;
    private int ID;
    private float GPA;
    private int[] uniPreferences = new int[3];
    private boolean successfullyAllocated = false;

    Student(String name, int ID, float GPA, int[] uniPreferences){
        this.name = name;
        this.ID = ID;
        this.GPA = GPA;
        this.uniPreferences = uniPreferences;
    }

    public float getGPA() {
        return GPA;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public int[] getUniPreferences() {
        return uniPreferences;
    }

    public boolean getSuccessfullyAllocated(){
        return successfullyAllocated;
    }

    public void setSuccessfullyAllocated(boolean success){
        successfullyAllocated = success;
    }
}
