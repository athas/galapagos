package galapagos;

/**
 * A behavior using the suspicious variation of the tit for
 tat-strategy.
 */
public class SuspiciousTitForTat extends MemoryBehavior {
    protected Action defaultAction () {
        return Action.IGNORING;
    }
    
    /**
     * Remembers what this finch did.
     */
    public void response (Finch finch, Action action) {
        add(finch,action);
    }
    
    public Behavior clone() {
        return new SuspiciousTitForTat();
    }
    
    public String toString() {
        return "Suspicious Tit for Tat";
    }
}