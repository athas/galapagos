package galapagos;

/**
 * A simple finch-behavior that consequently ignores other finches.
 */
public class Cheater implements Behavior {
    /**
     * Will never clean the other Finch
     */
    public Action decide(Finch finch) {
        return Action.IGNORING;
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
        return new Cheater();
    }
    
    /**
     * @inheritDoc
     */
    public String toString() {
        return "Cheater";
    }
    
    /**
     * @inheritDoc
     */
    public final boolean equals(Object obj) {
    	return (obj instanceof Cheater);
    }
    
    /**
     * @inheritDoc
     */
    public int hashCode() {
    	return toString().hashCode();
    }
}
