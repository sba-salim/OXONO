package model;

public abstract class Token {
    private final Symbol s;
    public Token(Symbol s) {
        this.s = s;
    }
    protected Symbol getS() {
        return s;
    }
}
