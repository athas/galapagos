package galapagos;

import java.util.*;

public class ProbingTitForTat implements Behavior {
    private Map<Finch, Action> finches;

    private int count;

    Random rand = new Random();

    public ProbingTitForTat() {
        finches = new HashMap<Finch, Action>();
        count = 5;
    }

    public Action decide(Finch finch) {
        cleanFinches();
        if (count == 0) {
            if (rand.nextInt(2) == 0) {
                count = 5;
                return Action.IGNORING;
            }
        } else {
            --count;
        }

        if (finches.containsKey(finch)) {
            return finches.get(finch);
        } else
            return Action.CLEANING;
    }

    private void cleanFinches () {
        for (Finch finch : finches.keySet())
            if (finch.status() != FinchStatus.ALIVE) finches.remove(finch);
    }
    
    public void response(Finch finch, Action action) {
        finches.put(finch, action);
    }
    
    public Behavior clone()
    {
        return new ProbingTitForTat();
    }

}
