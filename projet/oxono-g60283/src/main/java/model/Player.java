package model;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private List<Pawn> hand;
    public Player(Color c) {
        this.hand = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            Pawn p = new Pawn(Symbol.X,c);
            hand.add(p);
            Pawn p2=new Pawn(Symbol.O, c);
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


    public void addPawn(Pawn pawn) {
        hand.add(pawn);
    }

    public void remove(Symbol s) {
        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).getS() == s) {
                hand.remove(i);
                return;
            }
        }
    }

}