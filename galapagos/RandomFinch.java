package galapagos;

import java.util.Random;

public class RandomFinch implements Behavior {
    Random random = new Random();
    /**
     * Chooses randomly between CLEANING and IGNORING the other finch
     */
    public Action decide(Finch finch) {
        int choice = random.nextInt(2);

        switch (choice) {
        case 0:
            return Action.CLEANING;
        case 1:
            return Action.IGNORING;        
            
        //Unused
        default:
            return Action.CLEANING;
        }
    }

    /**
     * 
     */
    public void response(Finch finch, Action action) {
        
    }
    
    public Behavior clone() {
        return new RandomFinch();
    }
    
    public String toString() {
        return "Random";
    }
}
