package galapagos;

/**
 * A behavior that always does the opposite of what the opponent did
 * last time!  The finch always cleans when faced with an unknown
 * opponent.
 */
public class InverseTitForTat extends ActionMemoryBehavior {
    protected Action defaultAction() {
        return Action.IGNORING;
    }
    
    /**
     * Remembers what this finch did.
     */
    public void response (Finch finch, Action action) {
        remember(finch,action);
    }

    /**
     * The inverse of what we remember.
     */
    public Action decide(Finch finch) {
        if (super.decide(finch) == Action.CLEANING)
            return Action.IGNORING;
        else return Action.CLEANING;
    }
    
    /**
     * A new instance of the tit-for-tat behavior.
     */
    public Behavior clone() {
        return new InverseTitForTat();
    }
    
    /**
     * A toString method.
     */
    public String toString() {
        return "Inverse Tit for Tat";
    }
    
    public boolean equals(Object obj) {
    	if(obj instanceof InverseTitForTat)
    		return true;
    	else
    		return false;
    }
    
    public int hashCode() {
    	return toString().hashCode();
    }
}