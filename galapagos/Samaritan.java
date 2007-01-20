package galapagos;

/**
 * A simple finch-behavior that consequently cleans other finches.
 */
public class Samaritan implements Behavior {
    private static final String DESCRIPTION = 
        "Always cleans other finches.";
    
    /**
     * @inheritDoc
     */
    public String description() {
        return DESCRIPTION;
    }
    
    /**
     * Will always clean the other Finch
     */
    public Action decide(Finch finch) {
        return Action.CLEANING; 
    }

    /**
     * Doesn't use the other finch's action.
     */
    public void response(Finch finch, Action action) {
        
    }
    
    /**
     * @inheritDoc
     */
    public Behavior clone() {
        return new Samaritan();
    }
    
    /**
     * @inheritDoc
     */
    public String toString() {
        return "Samaritan";
    }
    
    /**
     * @inheritDoc
     */
    public final boolean equals(Object obj) {
    	return (obj instanceof Samaritan);
    }
    
    /**
     * @inheritDoc
     */
    public int hashCode() {
    	return toString().hashCode();
    }
}
