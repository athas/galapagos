package galapagos;

public interface Behavior extends Cloneable {
    public Action decide (Finch finch);
    public void response (Finch finch, Action action);
    public Behavior clone();
      
    /**
     * The toString()-method must only depend on the runtime-class.
     */
    public String toString();
}