package galapagos;

public class Statistical extends MemoryBehavior<Statistical.Statistics> {
    protected class Statistics {
        public int helpedGotHelp;
        public int helpedTotal;
        public int didntHelpGotHelp;
        public int didntHelpTotal;
        public Action lastAction;
        public Action secondToLastAction;
        
        public Statistics () {
            helpedGotHelp = 0;
            helpedTotal = 0;
            didntHelpGotHelp = 0;
            didntHelpTotal = 0;
        }
    }
    
    public Action decide(Finch finch) {
        Statistics stat = recall(finch);
        if (stat == null) {
            stat = new Statistics();
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

    /*public Action decide(Finch finch) {
        Statistics stat = recall(finch);
        Action choice;
        
        if (stat == null) {
            stat = new Statistics();
            remember(finch, stat);
            choice = Action.CLEANING;
        } else if (stat.helpedTotal + stat.didntHelpTotal == 0)
            choice = Action.IGNORING;
        else if (stat.didntHelpTotal * 9 >= stat.helpedTotal)
            choice = Action.CLEANING;
        else if (stat.helpedTotal * 9 >= stat.didntHelpTotal)
            choice = Action.IGNORING;
        else if (((double)stat.helpedGotHelp) / stat.helpedTotal >= 
            ((double)stat.didntHelpGotHelp) / stat.didntHelpTotal)
            choice = Action.CLEANING;
        else
            choice = Action.IGNORING;
        
        stat.lastAction = choice;
        return choice;
    }*/

    public void response(Finch finch, Action action) {
        Statistics stat = recall(finch);
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
    
    public String toString() {
        return "Statistical";
    }
    
    public boolean equals(Object obj) {
        return obj instanceof Statistical;
    }
    
    public int hashCode() {
        return toString().hashCode();
    }
    
    public Behavior clone() {
        return new Statistical();
    }
}
