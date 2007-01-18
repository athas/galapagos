package galapagos;

/**
 * A Tit-for-tat variant with the default action being the only difference. 
 */
public class SuspiciousTitForTat extends ActionMemoryBehavior {
    protected Action defaultAction () {
        return Action.IGNORING;
    }
    
    /**
     * Remembers what this finch did.
     */
    public void response (Finch finch, Action action) {
        remember(finch,action);
    }
    
    /**
     * A new instance of the suspecious tit-for-tat behavior.
     */
    public Behavior clone() {
        return new SuspiciousTitForTat();
    }
    
    /**
     * A toString method.
     */
    public String toString() {
        return "Suspicious Tit for Tat";
    }
}