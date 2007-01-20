package galapagos;

/**
 * A behavior that always does the opposite of what the opponent did
 * last time! The finch always cleans when faced with an unknown
 * opponent.
 */
public class InverseTitForTat extends ActionMemoryBehavior {
    private static final String DESCRIPTION = 
        "<HTML>Does the opposite of what the other finch did<br>" +
        "when they last met. Cleans the first time.</HTML>";
    
    /**
     * @inheritDoc
     */
    public String description() {
        return DESCRIPTION;
    }
    
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
     * Will do the inverse of what we remembered. Clean for unknown finches.
     */
    public Action decide(Finch finch) {
        if (super.decide(finch) == Action.CLEANING)
            return Action.IGNORING;
        else
            return Action.CLEANING;
    }

    /**
     * @inheritDoc
     */
    public Behavior clone() {
        return new InverseTitForTat();
    }

    /**
     * @inheritDoc
     */
    public String toString() {
        return "Inverse Tit for Tat";
    }

    /**
     * @inheritDoc
     */
    public final boolean equals(Object obj) {
    	return (obj instanceof InverseTitForTat);
    }

    /**
     * @inheritDoc
     */
    public int hashCode() {
    	return toString().hashCode();
    }
}