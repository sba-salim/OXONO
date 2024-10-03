package g60283.dev.ascii.model;

public class AsciiPaint {
    private Drawing drawing;

    public AsciiPaint() {
        this.drawing = new Drawing(20, 10);
    }

    public AsciiPaint(int width, int height) {
        this.drawing = new Drawing(width, height);
    }

    public void addCircle(double centerX, double centerY, double radius, char color) {
        Point center = new Point(centerX, centerY);
        Shape circle = new Circle(center, radius, color);
        drawing.add(circle);
    }

    public void addRectangle(double upperLeftX, double upperLeftY, double width, double height, char color) {
        Point upperLeft = new Point(upperLeftX, upperLeftY);
        Shape rectangle = new Rectangle(upperLeft, width, height, color);
        drawing.add(rectangle);
    }

    public void addSquare(double upperLeftX, double upperLeftY, double side, char color) {
        Point upperLeft = new Point(upperLeftX, upperLeftY);
        Shape square = new Square(upperLeft, side, color);
        drawing.add(square);
    }

    public char getColorAt(int x, int y) {
        Point p = new Point(x,y);
        Shape s = drawing.getShapeAt(p);
        if (s == null)
            return ' ';
        return s.getColor();
    }
    public int getWidth() {
        return drawing.getWidth();
    }
    public int getHeight() {
        return drawing.getHeight();
    }
    public void setColor(int index, char color) {
        drawing.getShapeAt(index).setColor(color);
    }
    public void moveShape(int index, double dx, double dy) {
        drawing.getShapeAt(index).move(dx, dy);
    }
    public void removeShape(int index) {
        drawing.remove(index);
    }

}
