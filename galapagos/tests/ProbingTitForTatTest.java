package galapagos.tests;

import galapagos.biotope.*;
import galapagos.behaviors.*;

/**
 * Test of the Probing Tit For Tat behavior.
 */
public class ProbingTitForTatTest extends BehaviorTest {
	
	/**
	 * Test that the Probing tit for tat does as expected the first
	 * few rounds, after the third response of each behavior
	 * it gets unpredictable. And we can't test that.
	 */
	public void testDecide() {
		// should clean unknown finches
    	assertEquals(behavior.decide(opponent), Action.CLEANING);
        assertEquals(behavior.decide(opponent2), Action.CLEANING);
        
        // Should do what opponent did last time in the first few rounds.
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
        
        // Thereafter it get unpredictable.
        // We cannot test the random qualities of Probing Tit-for-tat.
    }
    
    public Behavior getBehavior() {
        return new ProbingTitForTat();
    }
    
    public String behaviorName () {
    	return "Probing Tit for Tat";
    }
}
