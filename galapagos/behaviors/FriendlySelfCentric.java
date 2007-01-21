package galapagos.behaviors;

import galapagos.biotope.*;

/**
 * A behavior that will try to identify finches of its own species based
 * on their first actions. If it thinks another finch is also FriendlySelfCentric,
 * then it would always help it, if it can't be a FriendlySelfCentric finch then it
 * only helps it in the identification process.
 * As the SelfCentric Behavior, it identifies it self by using a start-scheme that not 
 * many other finches use.
 * At first it cleans the finch, then it ignores it two times. 
 * From this simple pattern it can distinguish it self from many of the other finches, 
 * though compared to SelfCentric this indentification scheme is more likely to be used
 * by other behaviors.
 */
public class FriendlySelfCentric extends MemoryBehavior<FriendlySelfCentric.Memory> {
    /**
     * An internal class for organising the data to remember for the
     * finches that this FriendlySelfCentric meets.
     */
    protected class Memory {
        public int meetingNumber;
        public boolean mightBeSameBehavior;
        
        public Memory () {
            meetingNumber = 0;
            mightBeSameBehavior = true;
        }
    }
    
    private static final String DESCRIPTION = 
        "<HTML>Tries to only help other Friendly Self-Centrics.<br>" +
        "Initially cleans other finches.</HTML>";
    private Action lastAction;
    
    /**
     * @inheritDoc
     */
    public String description() {
        return DESCRIPTION;
    }
    
    /**
     * The FriendlySelfCentric tries to identify members of its own Behavior using
     * a three meeting identification scheme. After the initial meetings it 
     * only cleans finches that has a possibility of being FriendlySelfCentric.
     */
    public Action decide(Finch finch) {
        Memory stat = recall(finch);
        
        if (stat == null) {
            // We have never met this finch before.
            stat = new Memory();
            remember(finch, stat);
            stat.meetingNumber++;
            lastAction = Action.CLEANING;
            return Action.CLEANING;
        } else if (stat.meetingNumber == 1) {
            // The second meeting with this finch.
            lastAction = Action.IGNORING;
            stat.meetingNumber++;
            return Action.IGNORING;
        } else if (stat.meetingNumber == 2) {
            // The third meeting with this finch.
            lastAction = Action.IGNORING;
            stat.meetingNumber++;
            return Action.IGNORING;
        } else if (stat.mightBeSameBehavior) {
            // If the other finch might be a FriendlySelfCentric, we help it.
            lastAction = Action.CLEANING;
            return Action.CLEANING;
        } else {
            // If we are sure that the other finch isn't a FriendlySelfCentric we ignore it.
            lastAction = Action.IGNORING;
            return Action.IGNORING;
        }
    }

    /**
     * Calculates whether the other finch deviates from the FriendlySelfCentric 
     * identification pattern.
     */
    public void response(Finch finch, Action action) {
        Memory stat = recall(finch);
        
        // If the other finch keeps making the same decisions as us,
        // it might be another FriendlySelfCentric.
        stat.mightBeSameBehavior = stat.mightBeSameBehavior && (action == lastAction);
    }
    
    /**
     * @inheritDoc
     */
    public String toString() {
        return "Friendly Self-Centric";
    }
    
    /**
     * @inheritDoc
     */
    public boolean equals(Object obj) {
        return obj instanceof FriendlySelfCentric;
    }
    
    /**
     * @inheritDoc
     */
    public int hashCode() {
        return toString().hashCode();
    }
    
    /**
     * @inheritDoc
     */
    public Behavior clone() {
        return new FriendlySelfCentric();
    }
}
