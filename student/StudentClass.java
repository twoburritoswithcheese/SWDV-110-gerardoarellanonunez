package student;

public class StudentClass {
    // fields
    private String name;
    private int id;
    private double gpa;

    // Constructor
    public StudentClass(String name, int id, double gpa) {
        this.name = name;
        this.id = id;
        this.gpa = gpa;
    }

    // Getters
    public String getName() {return this.name;}
    public int getId() {return this.id;}
    public double getGpa() {return this.gpa;}

    public void setGpa(double gpa) {
        // rejects values outside 0.0-4.0
        if (gpa >= 0.0 && gpa <= 4.0) {
            this.gpa = gpa;
        } else {
            System.out.println("GPA must be between 0.0 - 4.0");
        }
    }

    public String getStudent() {
        return String.format("Name: %s, ID: %d, GPA: %.2f", this.name, this.id, this.gpa);
    }
}