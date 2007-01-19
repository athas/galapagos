package galapagos;

/**
 * A finch behavior that randomly chooses between cleaning and ignoring other finches.
 */
public class RandomFinch implements Behavior {
    /**
     * Chooses randomly between CLEANING and IGNORING other finches.
     */
    public Action decide(Finch finch) {
        int choice = ((int) Math.random()) * 2;

        switch (choice) {
        case 0:
            return Action.CLEANING;
        case 1:
            return Action.IGNORING;        
            
        //Unused
        default:
            return Action.CLEANING;
        }
    }

    /**
     * Doesn't use the finch's action.
     */
    public void response(Finch finch, Action action) {
        
    }
    
    /**
     * A new RandomFinch behavior.
     */
    public Behavior clone() {
        return new RandomFinch();
    }
    
    /**
     * @inheritDoc
     */
    public String toString() {
        return "Random";
    }
    
    /**
     * @inheritDoc
     */
    public final boolean equals(Object obj) {
    	return (obj instanceof RandomFinch);
    }
    
    /**
     * @inheritDoc
     */
    public int hashCode() {
    	return toString().hashCode();
    }
}
