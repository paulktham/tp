package university;

public class University {
    private String fullName;
    private String acronym;
    private int SpotsLeft;

    public University(String fullName, String acronym, int spotsLeft) {
        this.fullName = fullName;
        this.acronym = acronym;
        this.SpotsLeft = spotsLeft;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAcronym() {
        return acronym;
    }

    public int getSpotsLeft() {
        return SpotsLeft;
    }

    public void removeASpot(){
        this.SpotsLeft--;
    }

    @Override
    public String toString() {
        return fullName + " (" + acronym + ")";
    }
}

