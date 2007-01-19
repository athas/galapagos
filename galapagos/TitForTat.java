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
     * Remembers what this finch did as the action
     * we wan't to use next time we meet it.
     */
    public void response (Finch finch, Action action) {
        remember(finch,action);
    }
    
    /**
     * A new Tit-for-tat behavior.
     */
    public Behavior clone() {
        return new TitForTat();
    }
    
    /**
     * @inheritDoc
     */
    public String toString() {
        return "Tit for Tat";
    }
    
    /**
     * @inheritDoc
     */
    public final boolean equals(Object obj) {
    	return (obj instanceof TitForTat);
    }
    
    /**
     * @inheritDoc
     */
    public int hashCode() {
    	return toString().hashCode();
    }
}