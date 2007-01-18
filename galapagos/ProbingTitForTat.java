package galapagos;

/**
 * A Tit-for-tat variant which some times (~ every fifth time) chooses to IGNORE the other finch.
 */
public class ProbingTitForTat extends ActionMemoryBehavior {
    private int count;

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
     * Remember what the finch did.
     */
    public void response(Finch finch, Action action) {
        remember(finch, action);
    }
    
    public Behavior clone()
    {
        return new ProbingTitForTat();
    }
    
    public String toString() {
        return "Probing Tit for Tat";
    }

}
