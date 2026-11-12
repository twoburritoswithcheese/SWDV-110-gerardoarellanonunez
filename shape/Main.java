package shape;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        // Store different shapes in one List
        List<Shape> shapes = new ArrayList<>();

        shapes.add(new Circle(6));
        shapes.add(new Rectangle(8, 15));
        shapes.add(new Square(4));

        // Print every shape using one loop
        for (Shape shape : shapes) {
            System.out.println(shape.getShape());
        }
    }
}