package galapagos;

import java.util.*;

/**
 * A behavior using the suspicious variation of the tit for
 tat-strategy.
 */
public class SuspiciousTitForTat implements Behavior {
    private Map<Finch,Action> finches;
    
    /**
     * Make a new tit for tat-behavior.
     */
    public SuspiciousTitForTat () {
        finches = new HashMap<Finch,Action> ();
    };
    
    /**
     * Decide what to do to the finch just met. If these finches has met before this finch
     * does what the other did last time.
     * @ensure finches.containsKey(finch) implies decide == finches.get(finch)
     */
    public Action decide (Finch finch) {
        cleanFinches();
        if (finches.containsKey(finch)) {
            return finches.get(finch);
        } else return Action.IGNORING;
    }
    
    private void cleanFinches () {
        for (Iterator<Finch> iterator = finches.keySet().iterator(); iterator.hasNext();)
            if (iterator.next().status() != FinchStatus.ALIVE)
                iterator.remove();
    }
    
    /**
     * Remembers what this finch did.
     */
    public void response (Finch finch, Action action) {
        finches.put(finch,action);
    }
    
    public Behavior clone() {
        return new SuspiciousTitForTat();
    }
    
    public String toString() {
        return "SuspiciousTitForTat";
    }
}