package g60283.dev.ascii.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Drawing {
    private int width;
    private int height;
    private List<Shape> shapes;

    public Drawing(int width, int height) {
        this.width = width;
        this.height = height;
        this.shapes = new ArrayList<>();
    }

   protected void add(Shape shape) {
        shapes.add(shape);
   }
   protected boolean remove(Shape shape) {
        return shapes.remove(shape);
   }
   protected boolean remove(int index) {
        try {
            shapes.remove(index);
            return true;
        } catch (Exception e) {
            return false;
        }
   }
    protected Shape getShapeAt(Point point) {
        for(Shape s : shapes) {
            if(s.isInside(point)) {
                return s;
            }
        }
        return null;
    }

    protected Shape getShapeAt(int index) {
        return shapes.get(index);
    }
    public int getWidth() {
        return width;
    }

    public List<Shape> getShapes() {
        return Collections.unmodifiableList(shapes);
    }

    public int getHeight() {
        return height;
    }
}
