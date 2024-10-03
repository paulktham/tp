package student;

public class Student {
    private String name;
    private int id;
    private float gpa;
    private int[] uniPreferences = new int[3];
    private boolean isSuccessfullyAllocated = false;

    Student(String name, int id, float gpa, int[] uniPreferences){
        this.name = name;
        this.id = id;
        this.gpa = gpa;
        this.uniPreferences = uniPreferences;
    }

    public float getGpa() {
        return gpa;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int[] getUniPreferences() {
        return uniPreferences;
    }

    public boolean getSuccessfullyAllocated(){
        return isSuccessfullyAllocated;
    }

    public void setSuccessfullyAllocated(boolean success){
        isSuccessfullyAllocated = success;
    }
}
