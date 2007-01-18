package galapagos;

/**
 * A behavior that always does what the opponent did last time!
 * The finch always cleans when faced with an unknown opponent.
 */
public class TitForTat extends ActionMemoryBehavior {
    protected Action defaultAction() {
        return Action.CLEANING;
    }
    
    /**
     * Remembers what this finch did.
     */
    public void response (Finch finch, Action action) {
        remember(finch,action);
    }
    
    /**
     * A new instance of the tit-for-tat behavior.
     */
    public Behavior clone() {
        return new TitForTat();
    }
    
    /**
     * A toString method.
     */
    public String toString() {
        return "Tit for Tat";
    }
}