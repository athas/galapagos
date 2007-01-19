package galapagos.tests;

import galapagos.*;

/**
 * Test of the Tit For Tat behavior.
 */
public class TitForTatTest extends BehaviorTest {
	
	/**
	 * Tests that the TitForTat always does the same as 
	 * what the other finch did last time they met.
	 * And that it cleans unknown finches
	 */
	public void testDecide() {
		// should clean unknown finches
    	assertEquals(behavior.decide(opponent), Action.CLEANING);
        assertEquals(behavior.decide(opponent2), Action.CLEANING);
        
        // Should do what opponent did last time.
        behavior.response(opponent, Action.IGNORING);
        assertEquals(behavior.decide(opponent), Action.IGNORING);
        
        behavior.response(opponent2, Action.CLEANING);
        assertEquals(behavior.decide(opponent2), Action.CLEANING);
        
        behavior.response(opponent, Action.CLEANING);
        assertEquals(behavior.decide(opponent), Action.CLEANING);
        
        behavior.response(opponent2, Action.IGNORING);
        assertEquals(behavior.decide(opponent2), Action.IGNORING);
        
        behavior.response(opponent, Action.CLEANING);
        assertEquals(behavior.decide(opponent), Action.CLEANING);
        
        behavior.response(opponent2, Action.IGNORING);
        assertEquals(behavior.decide(opponent2), Action.IGNORING);
    }
    
    public Behavior getBehavior() {
        return new TitForTat();
    }
    
    public String behaviorName () {
    	return "Tit for Tat";
    }
}
