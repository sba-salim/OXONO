package model;

public class Pawn extends Token {
    private final Color c;

    public Color getC() {
        return c;
    }

    public Pawn(Symbol s, Color c){
        super(s);
        this.c = c;
    }
}
