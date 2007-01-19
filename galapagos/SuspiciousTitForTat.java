package galapagos;

/**
 * A Tit-for-tat variant with the default action being the only difference. 
 */
public class SuspiciousTitForTat extends ActionMemoryBehavior {
    protected Action defaultAction () {
        return Action.IGNORING;
    }
    
    /**
     * Remembers what this finch did as the action
     * we wan't to use next time we meet it.
     */
    public void response (Finch finch, Action action) {
        remember(finch,action);
    }
    
    /**
     * A new Suspecious tit-for-tat behavior.
     */
    public Behavior clone() {
        return new SuspiciousTitForTat();
    }
    
    /**
     * @inheritDoc
     */
    public String toString() {
        return "Suspicious Tit for Tat";
    }
    
    /**
     * @inheritDoc
     */
    public boolean equals(Object obj) {
    	return (obj instanceof SuspiciousTitForTat);
    }
    
    /**
     * @inheritDoc
     */
    public int hashCode() {
    	return toString().hashCode();
    }
}