package galapagos.tests;

import galapagos.behaviors.*;
import galapagos.biotope.*;

public class InverseTitForTatTest extends BehaviorTest {

	/**
	 * Tests that the InverseTitForTat always does the opposite of 
	 * what the other finch did last time they met.
	 * And that it cleans unknown finches.
	 */
	public void testDecide() {
		// should clean unknown finches
    	assertEquals(behavior.decide(opponent), Action.CLEANING);
        assertEquals(behavior.decide(opponent2), Action.CLEANING);
        
        // Should do the opposite of what the opponent did last time.
        behavior.response(opponent, Action.IGNORING);
        assertEquals(behavior.decide(opponent), Action.CLEANING);
        
        behavior.response(opponent2, Action.CLEANING);
        assertEquals(behavior.decide(opponent2), Action.IGNORING);
        
        behavior.response(opponent, Action.CLEANING);
        assertEquals(behavior.decide(opponent), Action.IGNORING);
        
        behavior.response(opponent2, Action.IGNORING);
        assertEquals(behavior.decide(opponent2), Action.CLEANING);
        
        behavior.response(opponent, Action.CLEANING);
        assertEquals(behavior.decide(opponent), Action.IGNORING);
        
        behavior.response(opponent2, Action.IGNORING);
        assertEquals(behavior.decide(opponent2), Action.CLEANING);
    }
    
    public Behavior getBehavior() {
        return new InverseTitForTat();
    }
    
    public String behaviorName () {
    	return "Inverse Tit for Tat";
    }
}
