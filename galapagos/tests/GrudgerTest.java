package galapagos.tests;

import galapagos.*;

/**
 * Test of the Grudger behavior.
 */
public class GrudgerTest extends BehaviorTest {
	
	/**
	 * Tests that the decide-method returns Action.CLEANING until
	 * the opponent ignores. Thereafter it should return Action.IGNORING.
	 */
    public void testDecide() {
    	// Should clean unknown finches
    	assertEquals(behavior.decide(opponent), Action.CLEANING);
        assertEquals(behavior.decide(opponent2), Action.CLEANING);
    	
    	// The grudger should clean the finches that cleans it.
        for(int i = 0; i < 12; i++) {
        	behavior.response(opponent, Action.CLEANING);
            assertEquals(behavior.decide(opponent), Action.CLEANING);
            
            behavior.response(opponent2, Action.CLEANING);
            assertEquals(behavior.decide(opponent2), Action.CLEANING);
        }
        
        // should start ignoring an "opponent" when it gets ignored
        for(int i = 0; i < 12; i++) {
            behavior.response(opponent, Action.IGNORING);
            assertEquals(behavior.decide(opponent), Action.IGNORING);
            
            //should still clean the other opponents
            behavior.response(opponent2, Action.CLEANING);
            assertEquals(behavior.decide(opponent2), Action.CLEANING);
        }
        
        // shouldnt stop ignoring just beacause the opponent cleans again.
        behavior.response(opponent, Action.CLEANING);
        assertEquals(behavior.decide(opponent), Action.IGNORING);
    }
    
    public Behavior getBehavior() {
        return new Grudger();
    }
    
    public String behaviorName () {
    	return "Grudger";
    }
}
