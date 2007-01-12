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
     * 
     */
    public void response(Finch finch, Action action)
    {
        
    }
}
