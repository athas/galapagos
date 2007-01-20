package galapagos.behaviors;

import galapagos.biotope.*;

/**
 * A Tit-for-tat variant which some times (approximately every fifth time) chooses to IGNORE the other finch.
 */
public class ProbingTitForTat extends ActionMemoryBehavior {
    private static final String DESCRIPTION = 
        "<HTML>Normally does what the other finch did when<br>" +
        "they last met (and cleans the first meeting),<br>" +
        "but at random intervals it ignores anyway.</HTML>";
    
    private int count;

    /**
     * @inheritDoc
     */
    public String description() {
        return DESCRIPTION;
    }
    
    /**
     * A new PropingTitForTat.
     */
    public ProbingTitForTat() {
        count = 5;
    }

    /**
     * If count is positive we decrease it and choose the same action as a TitForTat would choose.
     * If it is 0 we choose 0 or 1 randomly, and ignores in the case 0, otherwice
     * we do what a TitForTat would do.
     */
    public Action decide(Finch finch) {
        //create a random value between 0 and 1
        int random = (int)Math.floor(Math.random() * 2);
        
        if (count == 0 && random == 0) {
            count = 5;
            return Action.IGNORING;
        } else {
            --count;
        }

        //do what the finch did to us last time
        return super.decide(finch);
    }
    
    /**
     * The default action of a probing tit-for-tat is CLEANING.
     */
    protected Action defaultAction () {
        return Action.CLEANING;
    }

    /**
     * Remember what the finch did as the Action we might do to 
     * that finch the next time we meet it.
     */
    public void response(Finch finch, Action action) {
        remember(finch, action);
    }
    
    /**
     * A new instance of the Probing Tit-for-tat behavior.
     */
    public Behavior clone()
    {
        return new ProbingTitForTat();
    }
    
    /**
     * @inheritDoc
     */
    public String toString() {
        return "Probing Tit for Tat";
    }

    /**
     * @inheritDoc
     */
    public boolean equals(Object obj) {
    	return (obj instanceof ProbingTitForTat);
    }
    
    /**
     * @inheritDoc
     */
    public int hashCode() {
    	return toString().hashCode();
    }
}
