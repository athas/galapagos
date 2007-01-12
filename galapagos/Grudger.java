package galapagos;

import java.util.*;

public class Grudger implements Behavior {
	Set enemies;
	
	public Grudger () {
		enemies = new HashSet();
	}
	
    /**
     * 
     */
    public Action decide(Finch finch) {
        if (enemies.contains(finch))
        	return Action.IGNORING;
        else
        	return Action.CLEANING;
    }

    /**
     * 
     */
    public void response(Finch finch, Action action) {
        if (action == Action.IGNORING)
        	enemies.add(finch);
    }
}