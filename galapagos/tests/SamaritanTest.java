package galapagos.tests;

import galapagos.*;

/**
 * Test of the Samaritan behavior.
 */
public class SamaritanTest extends BehaviorTest {
	
	/**
	 * Tests that the decide-method returns Action.CLEANING no matter what.
	 */
    public void testDecide() {
        assertEquals(behavior.decide(opponent), Action.CLEANING);
        
        //should also return CLEANING after response()-calls
        behavior.response(opponent, Action.CLEANING);
        assertEquals(behavior.decide(opponent), Action.CLEANING);
        
        behavior.response(opponent, Action.IGNORING);
        assertEquals(behavior.decide(opponent), Action.CLEANING);
    }
    
    public Behavior getBehavior() {
        return new Samaritan();
    }
    
    public String behaviorName () {
    	return "Samaritan";
    }
}
