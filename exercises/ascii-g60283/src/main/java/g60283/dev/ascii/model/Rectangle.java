package g60283.dev.ascii.model;

public class Rectangle extends  ColoredShape{
    private Point upperLeft;
    private double width;
    private double height;
    //*todo : Demander aux prof s'il y'a des modifs à apporter sur le constructeur.
    public Rectangle(Point upperLeft, double width, double height, char color) {
        super(color);
        this.upperLeft = new Point(upperLeft);
        this.width = width;
        this.height = height;
    }

    @Override
    public void move(double dx, double dy) {
        upperLeft.move(dx, dy);
    }

    @Override
    public boolean isInside(Point p) {
        return false;
    }
}
