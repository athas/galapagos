package galapagos.tests;

import galapagos.*;

/**
 * Test of the Cheater behavior.
 */
public class CheaterTest extends BehaviorTest {
	
	/**
	 * Tests that the decide-method returns Action.IGNORING no matter what.
	 */
    public void testDecide()
    {
        assertEquals(behavior.decide(opponent), Action.IGNORING);
        
        //should also return IGNORING after response()-calls
        behavior.response(opponent, Action.CLEANING);
        assertEquals(behavior.decide(opponent), Action.IGNORING);
        
        behavior.response(opponent, Action.IGNORING);
        assertEquals(behavior.decide(opponent), Action.IGNORING);
    }
    
    public Behavior getBehavior() {
        return new Cheater();
    }
    
    public String behaviorName() {
    	return "Cheater";
    }
}
