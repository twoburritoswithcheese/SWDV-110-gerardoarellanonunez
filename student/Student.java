package student;

import java.util.List;

public class Student {
    public static void main(String[] args) {
        
        List<StudentClass> students = List.of(
        new StudentClass("Bill", 123, 2.36),
        new StudentClass("Joe", 134, 2.97),
        new StudentClass("Tom", 145, 3.06));

        for (StudentClass student : students) {
            System.out.println(student.getStudent()); 
        }
    }
}
