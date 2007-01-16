package galapagos;

import java.util.*;

/**
 * A probing tit-for-tat variant.
 */
public class ProbingTitForTat extends MemoryBehavior {
    private int count;

    Random rand = new Random();

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
        if (count == 0) {
            if (rand.nextInt(2) == 0) {
                count = 5;
                return Action.IGNORING;
            }
        } else {
            --count;
        }

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
        add(finch, action);
    }
    
    public Behavior clone()
    {
        return new ProbingTitForTat();
    }
    
    public String toString() {
        return "Probing Tit for Tat";
    }

}
