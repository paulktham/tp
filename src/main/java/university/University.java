package university;

public class University {
    private String fullName;
    private String acronym;
    private int spotsLeft;

    public University(String fullName, String acronym, int spotsLeft) {
        this.fullName = fullName;
        this.acronym = acronym;
        this.spotsLeft = spotsLeft;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAcronym() {
        return acronym;
    }

    public int getSpotsLeft() {
        return spotsLeft;
    }

    public void removeASpot(){
        this.spotsLeft--;
    }

    @Override
    public String toString() {
        return fullName + " (" + acronym + ")";
    }
}

