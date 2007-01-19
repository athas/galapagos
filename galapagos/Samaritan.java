package galapagos;

public class Samaritan implements Behavior {
    /**
     * Will always clean the other Finch
     */
    public Action decide(Finch finch) {
        return Action.CLEANING; 
    }

    /**
     * Doesn't use the finch's action.
     */
    public void response(Finch finch, Action action) {
        
    }
    
    /**
     * A new instance of the samaritan behavior.
     */
    public Behavior clone() {
        return new Samaritan();
    }
    
    /**
     * A toString method.
     */
    public String toString() {
        return "Samaritan";
    }
    
    public boolean equals(Object obj) {
    	if(obj instanceof Samaritan)
    		return true;
    	else
    		return false;
    }
    
    public int hashCode() {
    	return toString().hashCode();
    }
}
