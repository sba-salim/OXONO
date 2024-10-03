package g60283.dev.ascii.model;

public interface Shape {
    void move (double dx, double dy);
    boolean isInside(Point p);
    char getColor();
    void setColor(char color);
}
