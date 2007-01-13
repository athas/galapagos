package galapagos;

public class Samaritan implements Behavior {
    /**
     * Will always clean the other Finch
     */
    public Action decide(Finch finch) {
        return Action.CLEANING; 
    }

    /**
     * 
     */
    public void response(Finch finch, Action action) {
        
    }
    
    public Behavior clone() {
        return new Samaritan();
    }
}
