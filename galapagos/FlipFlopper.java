package galapagos;

public class FlipFlopper implements Behavior {
    Action last = Action.IGNORING;
    /**
     * 
     */
    public Action decide(Finch finch) {
        last = (last == Action.IGNORING) ? Action.CLEANING : Action.IGNORING;
        return last;
    }

    /**
     * Doesn't use the other finch's action.
     */
    public void response(Finch finch, Action action) {
        
    }
    
    /**
     * A new FlipFlopper-behavior.
     */
    public Behavior clone() {
        return new FlipFlopper();
    }
    
    /**
     * A toString method.
     */
    public String toString() {
        return "Flip-Flopper";
    }
    
    public boolean equals(Object obj) {
    	if(obj instanceof FlipFlopper)
    		return true;
    	else
    		return false;
    }
    
    public int hashCode() {
    	return toString().hashCode();
    }
}
