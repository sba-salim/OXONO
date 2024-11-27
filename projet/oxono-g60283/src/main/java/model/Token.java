package model;

public abstract class Token {
    private Symbol s;
    public Token(Symbol s) {
        this.s = s;
    }
    protected Symbol getS() {
        return s;
    }
}
