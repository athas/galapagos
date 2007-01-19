package galapagos;

public class Grudger extends ActionMemoryBehavior {
    /**
     * If a finch just met ignores this finch it should be remembered.
     */
    public void response(Finch finch, Action action) {
        if (action == Action.IGNORING)
            remember(finch, Action.IGNORING);
    }
    
    /**
     * What a grudger should do to a finch which has't ignored it.
     */
    protected Action defaultAction() {
        return Action.CLEANING;
    }
    
    /**
     * A new Grudger.
     */
    public Behavior clone() {
        return new Grudger();
    }
    
    /**
     * A toString method.
     */
    public String toString() {
        return "Grudger";
    }
    
    public boolean equals(Object obj) {
    	if(obj instanceof Grudger)
    		return true;
    	else
    		return false;
    }
    
    public int hashCode() {
    	return toString().hashCode();
    }
}