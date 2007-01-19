package galapagos;

/**
 * A simple behavior that switches between ignoring and cleaning
 * the finches it meets.
 */
public class FlipFlopper implements Behavior {
    Action last = Action.IGNORING;

    /**
     * Will do the opposite of what it did last time.
     */
    public Action decide(Finch finch) {
        last = (last == Action.IGNORING)
        			? Action.CLEANING
        		    : Action.IGNORING;
        return last;
    }

    /**
     * Doesn't use the other finch's action.
     */
    public void response(Finch finch, Action action) {
        
    }
    
    /**
     * A new FlipFlopper-behavior.
     */
    public Behavior clone() {
        return new FlipFlopper();
    }
    
    /**
     * @inheritDoc
     */
    public String toString() {
        return "Flip-Flopper";
    }
    
    /**
     * @inheritDoc
     */
    public final boolean equals(Object obj) {
    	return (obj instanceof FlipFlopper);
    }
    
    /**
     * @inheritDoc
     */
    public int hashCode() {
    	return toString().hashCode();
    }
}
