package galapagos;

/**
 * A Behavior that estimates the conditional probabilities of being helped 
 * when either helping or ignoring a given other finch, and uses this
 * information to decide what to do.
 */
public class Statistical extends MemoryBehavior<Statistical.Memory> {
    /**
     * An internal class for organising the data to remember for the
     * finches that this Statistical meets.
     */
    protected class Memory {
        public int helpedGotHelp;
        public int helpedTotal;
        public int didntHelpGotHelp;
        public int didntHelpTotal;
        public Action lastAction;
        public Action secondToLastAction;
        
        public Memory () {
            helpedGotHelp = 0;
            helpedTotal = 0;
            didntHelpGotHelp = 0;
            didntHelpTotal = 0;
        }
    }
    
    private static final String DESCRIPTION = 
        "<HTML>Tries to analyse the best action against a given<br>" +
        "finch based on estimates of the conditional<br>" +
        "probabilities of beeing helped when the<br>" +
        "Statistical helps og ignores the other finch.</HTML>";
    
    /**
     * @inheritDoc
     */
    public String description() {
        return DESCRIPTION;
    }
    
    /**
     * The Statistical behavior tries to calculate the conditional probabilities
     * of being helped by the specified finch next turn in the cases that this 
     * behavior decides to clean, or decides to ignore the specified finch.
     * The probabilities are estimated using memory of earlier encounters with the 
     * same finch.
     */
    public Action decide(Finch finch) {
        Memory stat = recall(finch);
        if (stat == null) {
            stat = new Memory();
            remember(finch, stat);
            stat.lastAction = Action.CLEANING;
            return Action.CLEANING;
        } else if (stat.helpedTotal + stat.didntHelpTotal == 0) {
            stat.lastAction = Action.IGNORING;
            return Action.IGNORING;
        }
        
        double pointsForCleaning = ((double)stat.helpedGotHelp)/stat.helpedTotal * 3.0;
        double pointsForIgnoring = ((double)stat.didntHelpGotHelp)/stat.didntHelpTotal * 4.0 + 1;
        if (pointsForCleaning >= pointsForIgnoring) {
            stat.lastAction = Action.CLEANING;
            return Action.CLEANING;
        } else {
            stat.lastAction = Action.IGNORING;
            return Action.IGNORING;
        }
    }

    /**
     * Takes the Action made by the specified finch, and pair it with this behaviors 
     * action from the last round. This pair of an action and a reaction is used with earlier 
     * memory to determine which action to take at the next meeting with the specified finch.
     */
    public void response(Finch finch, Action action) {
        Memory stat = recall(finch);
        if (stat.secondToLastAction == Action.IGNORING) {
            stat.didntHelpTotal++;
            if (action == Action.CLEANING) {
                stat.didntHelpGotHelp++;
            }
        } else if (stat.secondToLastAction == Action.CLEANING){
            stat.helpedTotal++;
            if (action == Action.CLEANING) {
                stat.helpedGotHelp++;
            }
        }
        
        stat.secondToLastAction = stat.lastAction;
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
    
    /**
     * @inheritDoc
     */
    public Behavior clone() {
        return new Statistical();
    }
}
