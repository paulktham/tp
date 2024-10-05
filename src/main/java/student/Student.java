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
        this.uniPreferences = uniPreferences;
    }

    // copy constructor
    public Student(Student other) {
        this.id = other.id;
        this.gpa = other.gpa;
        this.uniPreferences = new ArrayList<>(other.uniPreferences.size());
        for (Integer preference : other.uniPreferences) {
            this.uniPreferences.add(preference);
        }
        this.isSuccessfullyAllocated = other.isSuccessfullyAllocated;
        this.allocatedUniversity = other.allocatedUniversity;
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
