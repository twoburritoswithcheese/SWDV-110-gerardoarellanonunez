package shape;


class Square extends Rectangle {

    Square(double side) {
        super(side, side);
    }

    @Override
    String getShape() {
        return "Square: " + area();
    }
}