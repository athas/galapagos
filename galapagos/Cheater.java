package galapagos;

public class Cheater implements Behavior
{
    /**
     * Will never clean the other Finch
     */
    public Action decide(Finch finch)
    {
        return Action.IGNORING;
    }

    /**
     * Doesn't use the other finch's action.
     */
    public void response(Finch finch, Action action)
    {
        
    }
    
    /**
     * A new cheater behavior.
     */
    public Behavior clone() {
        return new Cheater();
    }
    
    /**
     * A toString method.
     */
    public String toString() {
        return "Cheater";
    }
}
