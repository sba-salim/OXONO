package g60283.dev.ascii.model;

public class Circle extends ColoredShape{
    private Point center;
    private double radius;
    Circle(Point center, double radius, char color) {
        super(color);
        this.center = new Point(center);
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public void move(double dx, double dy) {
        center.move(dx, dy);
    }

    public Point getCenter() {
        return new Point(center);
    }

    @Override
    public boolean isInside(Point p) {
        double distance = center.distanceTo(p);
        return distance <= radius;
    }
}
