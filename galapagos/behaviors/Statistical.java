package galapagos.behaviors;

import galapagos.biotope.*;

/**
 * A Behavior that estimates the conditional probabilities of being
 * helped when either helping or ignoring a given other finch, and
 * uses this information to decide what to do.
 */
public class Statistical extends MemoryBehavior<Statistical.Memory> {
    /**
     * An internal class for organising the data to remember for the
     * finches that this Statistical meets.
     */
    protected class Memory {
        public int cleanedAndGotCleaned;
        public int cleanedTotal;
        public int ignoredAndGotCleaned;
        public int ignoredTotal;
        public Action lastAction;
        public Action secondToLastAction;
        public boolean mightBeStatistical;
        
        public Memory () {
            cleanedAndGotCleaned = 0;
            cleanedTotal = 0;
            ignoredAndGotCleaned = 0;
            ignoredTotal = 0;
            mightBeStatistical = true;
        }
    }
    
    private static final String DESCRIPTION = 
        "<HTML>Tries to analyse the best action against a given<br>" +
        "finch based on estimates of the conditional<br>" +
        "probabilities of being helped when the<br>" +
        "Statistical helps or ignores the other finch.</HTML>";
    
    private static final double weightOfGettingCleanedNextTurn = 2.0;
    
    /**
     * @inheritDoc
     */
    public String description() {
        return DESCRIPTION;
    }
    
    /**
     * The Statistical behavior tries to calculate the conditional
     * probabilities of being helped by the specified finch next turn
     * in the cases that this behavior decides to clean, or decides to
     * ignore the specified finch.  The probabilities are estimated
     * using memory of earlier encounters with the same finch.
     */
    public Action decide(Finch finch) {
        Memory stat = recall(finch);
        
        if (stat == null) {
            // We have never met this finch before.
            stat = new Memory();
            remember(finch, stat);
            stat.lastAction = Action.CLEANING;
            return Action.CLEANING;
        } else if (stat.secondToLastAction == null) {
            // The second meeting with this finch.
            stat.secondToLastAction = stat.lastAction;
            stat.lastAction = Action.IGNORING;
            return Action.IGNORING;
        } else if (stat.cleanedTotal == 0 || stat.ignoredTotal == 0) {
            // We don't have enough data to calculate the conditional
            // probabilities.
            stat.secondToLastAction = stat.lastAction;
            stat.lastAction = Action.IGNORING;
            return Action.IGNORING;
        } 

        // Now we have enough data to start analyzing them.
        
        // If the other finch might be another Statistical, we help
        // it.
        if (stat.mightBeStatistical) {
            stat.secondToLastAction = stat.lastAction;
            stat.lastAction = Action.CLEANING;
            return Action.CLEANING;
        }
        
        // We estimate the probability of other finch cleaning us if
        // we clean it, and if we ignore it.
        double probGettingCleanedWhenCleaning = 
            ((double) stat.cleanedAndGotCleaned) / stat.cleanedTotal;
        double probGettingCleanedWhenIgnoring = 
            ((double) stat.ignoredAndGotCleaned) / stat.ignoredTotal;
        
        // Based on our action last round, we estimate the probability
        // of getting cleaned this round.
        double probGettingCleanedThisRound;
        if (stat.lastAction == Action.CLEANING)
            probGettingCleanedThisRound = probGettingCleanedWhenCleaning;
        else
            probGettingCleanedThisRound = probGettingCleanedWhenIgnoring;
        
        // We compute the estimated number of points we can get this
        // round when cleaning, and when ignoring the other finch.
        double pointsThisRoundIfCleaning = probGettingCleanedThisRound * 3.0;
        double pointsThisRoundIfIgnoring = probGettingCleanedThisRound * 4.0 + 1.0;
        
        // We take into account that we would like to get cleaned next
        // round;
        double cleaningGoodness = pointsThisRoundIfCleaning + 
            probGettingCleanedWhenCleaning * weightOfGettingCleanedNextTurn;
        double ignoringGoodness = pointsThisRoundIfIgnoring + 
            probGettingCleanedWhenIgnoring * weightOfGettingCleanedNextTurn;
        
        // We make our choice.
        Action choice;
        if (cleaningGoodness >= ignoringGoodness)
            choice = Action.CLEANING;
        else
            choice = Action.IGNORING;
        
        // We save our choice in the memory.
        stat.secondToLastAction = stat.lastAction;
        stat.lastAction = choice;
        
        return choice;
    }

    /**
     * Takes the Action made by the specified finch, and pair it with
     * this behaviors action from the last round. This pair of an
     * action and a reaction is used with earlier memory to determine
     * which action to take at the next meeting with the specified
     * finch.
     */
    public void response(Finch finch, Action action) {
        Memory stat = recall(finch);
        
        // If the other finch keeps making the same decisions as us,
        // it might be another Statistical.
        stat.mightBeStatistical = 
            stat.mightBeStatistical && (action == stat.lastAction);
        
        if (stat.secondToLastAction == Action.IGNORING) {
            stat.ignoredTotal++;
            if (action == Action.CLEANING) {
                stat.ignoredAndGotCleaned++;
            }
        } else if (stat.secondToLastAction == Action.CLEANING){
            stat.cleanedTotal++;
            if (action == Action.CLEANING) {
                stat.cleanedAndGotCleaned++;
            }
        }
    }
    
    /**
     * @inheritDoc
     */
    public String toString() {
        return "Statistical";
    }
    
    /**
     * @inheritDoc
     */
    public boolean equals(Object obj) {
        return obj instanceof Statistical;
    }
    
    /**
     * @inheritDoc
     */
    public int hashCode() {
        return toString().hashCode();
    }
    
    /**e
     * @inheritDoc
     */
    public Behavior clone() {
        return new Statistical();
    }
}
