package galapagos;

/**
 * The Grudger cleans a specific finch as long as that finch cleans the Grudger.
 * Once a finch ignores the Grudger a single time, it is never cleaned by
 * that Grudger again. 
 */
public class Grudger extends ActionMemoryBehavior {
    private static final String DESCRIPTION = 
        "<HTML>Cleans a specific finch as long as the<br>" + 
        "Grudger is cleaned in return every time.</HTML>";
    
    /**
     * @inheritDoc
     */
    public String description() {
        return DESCRIPTION;
    }

    /**
     * If a finch just met ignores this finch it should be remembered
     * so that it can be ignored in future meetings.
     */
    public void response(Finch finch, Action action) {
        if (action == Action.IGNORING)
            remember(finch, Action.IGNORING);
    }
    
    /**
     * What a grudger should do to a finch which hasn't ignored it.
     */
    protected Action defaultAction() {
        return Action.CLEANING;
    }
    
    /**
     * @inheritDoc
     */
    public Behavior clone() {
        return new Grudger();
    }
    
    /**
     * @inheritDoc
     */
    public String toString() {
        return "Grudger";
    }
    
    /**
     * @inheritDoc
     */
    public final boolean equals(Object obj) {
    	return (obj instanceof Grudger);
    }
    
    /**
     * @inheritDoc
     */
    public int hashCode() {
    	return toString().hashCode();
    }
}