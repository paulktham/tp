package student;

import java.util.ArrayList;

public class Student {
    private String id;
    private float gpa;
    private ArrayList<Integer> uniPreferences;
    private boolean isSuccessfullyAllocated = false;
    private int allocatedUniversity = -1;

    public Student(String id, float gpa, ArrayList<Integer> uniPreferences){
        this.id = id;
        this.gpa = gpa;
        this.uniPreferences = new ArrayList<>();
    }

    public float getGpa() {
        return gpa;
    }

    public String getId() {
        return id;
    }

    public ArrayList<Integer> getUniPreferences() {
        return uniPreferences;
    }

    public boolean getSuccessfullyAllocated() {
        return isSuccessfullyAllocated;
    }

    public void setSuccessfullyAllocated(boolean success) {
        isSuccessfullyAllocated = success;
    }

    public void setAllocatedUniversity(int university) {
        this.allocatedUniversity = university;
    }

    public int getAllocatedUniversity() {
        return this.allocatedUniversity;
    }
}
