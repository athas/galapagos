package galapagos;

import java.util.*;

public class ProbingTitForTat extends MemoryBehavior {
    private int count;

    Random rand = new Random();

    public ProbingTitForTat() {
        count = 5;
    }

    public Action decide(Finch finch) {
        if (count == 0) {
            if (rand.nextInt(2) == 0) {
                count = 5;
                return Action.IGNORING;
            }
        } else {
            --count;
        }

        return super.decide(finch);
    }
    
    protected Action defaultAction () {
        return Action.CLEANING;
    }

    public void response(Finch finch, Action action) {
        add(finch, action);
    }
    
    public Behavior clone()
    {
        return new ProbingTitForTat();
    }
    
    public String toString() {
        return "Probing Tit for Tat";
    }

}
