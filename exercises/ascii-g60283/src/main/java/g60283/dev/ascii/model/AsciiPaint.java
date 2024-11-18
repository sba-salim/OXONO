package g60283.dev.ascii.model;

import java.util.*;

public class AsciiPaint {
    private final Drawing drawing;

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
    public void group(List<Integer> indexes) {
        List<Shape> shapes = new ArrayList<>();
        Collections.sort(indexes);
        System.out.println(indexes.size());
        for(int i : indexes) {
            shapes.add(drawing.getShapeAt(i));
        }
        for (int i = indexes.size()-1; i>-1  ; i--) {
            drawing.removeAtIndex(indexes.get(i));
        }
        Group group = new Group(shapes.getFirst().getColor());
        drawing.add(group);
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
        drawing.removeAtIndex(index);
    }
    public List<Shape> getShapes() {
        return drawing.getShapes();
    }

}
