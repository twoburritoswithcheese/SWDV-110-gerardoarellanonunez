
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

public class StudentRoster {
    public static void main(String[] args) {
        record Student(String name, double gpa, String major) {}

        List<Student> roster = new ArrayList<>(List.of(
            new Student("Ana", 3.8, "CS"), new Student("Kai", 3.2, "Math"),
            new Student("Zoe", 3.9, "CS"), new Student("Raj", 3.2, "CS"),
            new Student("Lee", 2.7, "Art"), new Student("Joe", 2.8, "Art"),
            new Student("Bill", 3.8, "Math"), new Student("Kim", 3.4, "Math")));
            
        roster.sort(Comparator.comparing(Student::name)
                             .thenComparing(Student::major)
                             .thenComparing(Student::gpa));
        
        roster.removeIf(s -> s.gpa() < 2.0);

        roster.forEach(s -> System.out.println(s.name().toUpperCase() 
                                               + " " + s.major() 
                                               + " " + s.gpa()));
    }  
}