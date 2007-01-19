package galapagos;

import java.util.LinkedList;

public class LenientGrudger extends ActionMemoryBehavior {
    private final LinkedList<Finch> blacklist;

    public LenientGrudger () {
        super();
        blacklist = new LinkedList<Finch>();
    }
    
    /**
     * If a finch just met ignores this lenient grudger finch twice it
     * should be remembered.
     */
    public void response(Finch finch, Action action) {
        if (action == Action.IGNORING)
            if (recall(finch) != null && recall(finch) == Action.IGNORING)
                blacklist.add(finch);
            else
                remember(finch, Action.IGNORING);
    }
    
    /**
     * What a lenient grudger should do to a finch that hasn't ignored
     * it twice.
     */
    protected Action defaultAction() {
        return Action.CLEANING;
    }

    public Action decide(Finch finch) {
        if (blacklist.contains(finch))
            return Action.IGNORING;
        else
            return defaultAction();
    }
    
    /**
     * A new LenientGrudger.
     */
    public Behavior clone() {
        return new LenientGrudger();
    }

    /**
     * A toString method.
     */
    public String toString() {
        return "Lenient Grudger";
    }
    
    public boolean equals(Object obj) {
    	if(obj instanceof LenientGrudger)
    		return true;
    	else
    		return false;
    }
    
    public int hashCode() {
    	return toString().hashCode();
    }
}