

public class Thinker {
    
    private String firstName;
    private String lastName;
    private int exam1;
    private int exam2;
    private int exam3;
    private int exam4;
    private int exam5;
    private int exam6;
    
    public Thinker(String lastName, String firstName, int exam1, int exam2, int exam3, int exam4, int exam5, int exam6) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.exam1 = exam1;
        this.exam2 = exam2;
        this.exam3 = exam3;
        this.exam4 = exam4;
        this.exam5 = exam5;
        this.exam6 = exam6;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
    
    public int getExam(int x) {
        if (x == 1) {
            return exam1;
        } else if (x == 2) {
            return exam2;
        } else if (x == 3) {
            return exam3;
        } else if (x == 4) {
            return exam4;
        } else if (x == 5) {
            return exam5;
        } else if (x == 6) {
            return exam6;
        } else {
            return -1;
        }
    }
}
