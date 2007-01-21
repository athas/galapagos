package galapagos.behaviors;

import galapagos.biotope.*;

import java.util.LinkedList;

/**
 * A modification of the Grudger behavior. If the same finch has ignored
 * it two times, it will consequently ignore that finch. 
 */
public class LenientGrudger extends ActionMemoryBehavior {
    private static final String DESCRIPTION = 
        "<HTML>Cleans a specific finch as long as the<br>" +
        "Lenient Grudger is ignored at most one time.</HTML>";
    
    private final LinkedList<Finch> blacklist;

    /**
     * @inheritDoc
     */
    public String description() {
        return DESCRIPTION;
    }
    
    public LenientGrudger () {
        super();
        blacklist = new LinkedList<Finch>();
    }
    
    /**
     * Blacklists finches that have ignored us two times.
     */
    public void response(Finch finch, Action action) {
        if (action == Action.IGNORING)
            if (recall(finch) != null && recall(finch) == Action.IGNORING)
                blacklist.add(finch);
            else
                remember(finch, Action.IGNORING);
    }
    
    /**
     * What a lenient grudger does to finches not on the blacklist.
     */
    protected Action defaultAction() {
        return Action.CLEANING;
    }

    /**
     * Ignores all blacklisted finches, the defaultAction is used againts the rest.
     */
    public Action decide(Finch finch) {
        if (blacklist.contains(finch))
            return Action.IGNORING;
        else
            return defaultAction();
    }
    
    /**
     * A new LenientGrudger-behavior.
     */
    public Behavior clone() {
        return new LenientGrudger();
    }

    /**
     * @inheritDoc
     */
    public String toString() {
        return "Lenient Grudger";
    }
    
    /**
     * @inheritDoc
     */
    public final boolean equals(Object obj) {
    	return (obj instanceof LenientGrudger);
    }
    
    /**
     * @inheritDoc
     */
    public int hashCode() {
    	return toString().hashCode();
    }
}