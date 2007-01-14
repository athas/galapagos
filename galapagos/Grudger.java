package galapagos;

public class Grudger extends MemoryBehavior {
    /**
     * 
     */
    public void response(Finch finch, Action action) {
        if (action == Action.IGNORING)
            add(finch, Action.IGNORING);
    }
    
    protected Action defaultAction() {
        return Action.CLEANING;
    }
    
    public Behavior clone() {
        return new Grudger();
    }
    
    public String toString() {
        return "Grudger";
    }
}