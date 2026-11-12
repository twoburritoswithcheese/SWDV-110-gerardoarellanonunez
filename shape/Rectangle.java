package shape;


class Rectangle extends Shape {
    protected double length;
    protected double width;

    Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }

    @Override
    double area() {
        return length * width;
    }

    @Override
    String getShape() {
        return "Rectangle: " + area();
    }
}