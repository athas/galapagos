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
    
    /**
     * The Action performed when nothing is known about the given finch.
     */
    protected Action defaultAction() {
        return Action.IGNORING;
    }

    /**
     * Remembers the opposite of what this finch did.
     */
    public void response (Finch finch, Action action) {
        if (action == Action.CLEANING)
            remember(finch,Action.IGNORING);
        else
            remember(finch,Action.CLEANING);
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