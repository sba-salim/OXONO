package g60283.dev.ascii.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Group extends ColoredShape {

    List<Shape> shapes;

    Group(char color) {
        super(color);
        shapes = new ArrayList<>();
    }

    @Override
    public void move(double dx, double dy) {
        for (Shape s : shapes) {
            s.move(dx, dy);
        }
    }

    @Override
    public boolean isInside(Point p) {
        for (Shape s : shapes) {
            if(s.isInside(p))
                return true;
        }
        return false;
    }

    protected void addShape(Shape s) {
        shapes.add(s);
    }

    public List<Shape> getShapes() {
        return Collections.unmodifiableList(shapes);
    }
}
