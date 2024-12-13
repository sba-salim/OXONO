package model;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private final List<Pawn> hand;
    private final Color c;

    public Player(Color c, int handSize) {
        this.c = c;
        this.hand = new ArrayList<>();
        for (int i = 0; i < handSize/2; i++) {
            Pawn p = new Pawn(Symbol.X, c);
            hand.add(p);
            Pawn p2 = new Pawn(Symbol.O, c);
            hand.add(p2);
        }

    }

    public boolean hasPawn(Symbol s) {
        for (Pawn pawn : hand) {
            if (pawn.getS() == s) {
                return true;
            }
        }
        return false;
    }

    public boolean isHandEmpty() {
        return this.hand.isEmpty();
    }

    public void addPawn(Pawn pawn) {
        hand.add(pawn);
    }

    public Pawn removePawn(Symbol s) {
        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).getS() == s) {
                return hand.remove(i);
            }
        }
        return null;
    }

    public Color getC() {
        return c;
    }
}