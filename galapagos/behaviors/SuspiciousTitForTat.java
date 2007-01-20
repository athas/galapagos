package galapagos.behaviors;

import galapagos.biotope.*;

/**
 * A Tit-for-tat variant with the default action being the only difference. 
 */
public class SuspiciousTitForTat extends ActionMemoryBehavior {
    private static final String DESCRIPTION = 
        "<HTML>Does what the other finch did when they<br>" +
        "last met. Ignores the first time.</HTML>";
    
    /**
     * @inheritDoc
     */
    public String description() {
        return DESCRIPTION;
    }
    
    /**
     * @inheritDoc
     */
    protected Action defaultAction () {
        return Action.IGNORING;
    }
    
    /**
     * Remembers what this finch did as the action
     * we want to use next time we meet it.
     */
    public void response (Finch finch, Action action) {
        remember(finch,action);
    }
    
    /**
     * @inheritDoc
     */
    public Behavior clone() {
        return new SuspiciousTitForTat();
    }
    
    /**
     * @inheritDoc
     */
    public String toString() {
        return "Suspicious Tit for Tat";
    }
    
    /**
     * @inheritDoc
     */
    public boolean equals(Object obj) {
    	return (obj instanceof SuspiciousTitForTat);
    }
    
    /**
     * @inheritDoc
     */
    public int hashCode() {
    	return toString().hashCode();
    }
}