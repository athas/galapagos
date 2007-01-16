package galapagos;

/**
 * The interface of the finch behaviors.
 */
public interface Behavior extends Cloneable {
    public Action decide (Finch finch);
    
    /**
     * This finch gets information about which action a finch just met chose.
     */
    public void response (Finch finch, Action action);
    
    /**
     * A new instanse of the same behavior.
     */
    public Behavior clone();
      
    /**
     * The toString()-method must only depend on the runtime-class.
     */
    public String toString();
}