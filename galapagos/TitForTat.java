package galapagos;

/**
 * A behavior using the tit for tat-strategy.
 */
public class TitForTat extends MemoryBehavior {
    protected Action defaultAction() {
        return Action.CLEANING;
    }
    
    /**
     * Remembers what this finch did.
     */
    public void response (Finch finch, Action action) {
        add(finch,action);
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