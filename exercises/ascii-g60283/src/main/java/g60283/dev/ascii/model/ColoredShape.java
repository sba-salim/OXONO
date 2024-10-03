package g60283.dev.ascii.model;

public abstract class ColoredShape implements Shape{
    private char color;
    ColoredShape(char color) {
        this.color=color;
    }

    @Override
    public void setColor(char color) {
        this.color = color;
    }

    @Override
    public char getColor() {
        return color;
    }
}
