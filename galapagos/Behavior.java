package galapagos;

/**
 * The interface of the finch behaviors.
 */
public interface Behavior extends Cloneable {
    /**
     * A short description of which strategy this Behavior uses.
     */
    public String description();
    
    /**
     * Let's this Behavior decide whether it wants to clean or ignore a given finch.
     * @param finch The finch to make a decision about.
     * @return The choosen action.
     */
    public Action decide (Finch finch);
    
    /**
     * Let's this Behavior do something (ex. remember the finch) when another finch 
     * has taken an action (cleaned or ignored) againt this finch.
     * @param finch The finch that has done something to this finch.
     * @param action What the finch did.
     */
    public void response (Finch finch, Action action);
    
    /**
     * Creates a new instance of the same behavior.
     * @return A new behavior of this type.
     */
    public Behavior clone();
      
    /**
     * The name of this kind of Behavior.
     * Must only depend on the runtime-class, by returning the same string for all
     * Behaviors of this kind.
     * @return The Behaviors name.
     */
    public String toString();
    
    /**
     * Indicates whether another Object is "equal" to this one, in the sense that
     * 2 Behaviors are "equal" if they are of the same type.
     * @param obj object to compare to.
     * @return true if obj is of the same Behavior-type as this one.
     */
    public boolean equals(Object obj);
    
    /**
     * Returns a hashCode for this Behavior. Must ensure that all Behaviors of this
     * type gives the same hashCode to fullfill the specification of Object.hashCode().
     * A good way of implementing this is by using the hashCode of the Behaviors name.
     * @return A hashCode that is equal for all instances of this Behavior. 
     */
    public int hashCode();
}