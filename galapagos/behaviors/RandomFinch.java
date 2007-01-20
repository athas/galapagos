package galapagos.behaviors;

import galapagos.biotope.*;

/**
 * A finch behavior that randomly chooses between cleaning and ignoring other finches.
 */
public class RandomFinch implements Behavior {
    private static final String DESCRIPTION = 
        "Chooses randomly whether to clean (50% chance) or ignore.";
    
    /**
     * @inheritDoc
     */
    public String description() {
        return DESCRIPTION;
    }
    
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
