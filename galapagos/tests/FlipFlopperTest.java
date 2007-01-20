package galapagos.tests;

import galapagos.behaviors.*;
import galapagos.biotope.*;

/**
 * Tests the FlipFlopper-behavior.
 */
public class FlipFlopperTest extends BehaviorTest {
	
	/**
	 * Tests that the decide-method gives Action.CLEANING the
	 * first time and thereafter switches between Action.IGNORING
	 * and Action.CLEANING.
	 */
    public void testDecide() {
        assertEquals(behavior.decide(opponent), Action.CLEANING);
        assertEquals(behavior.decide(opponent), Action.IGNORING);
        assertEquals(behavior.decide(opponent), Action.CLEANING);
        assertEquals(behavior.decide(opponent), Action.IGNORING);
        assertEquals(behavior.decide(opponent), Action.CLEANING);
        
        //response shouldnt affect the decision
        behavior.response(opponent, Action.IGNORING);
        assertEquals(behavior.decide(opponent), Action.IGNORING);
        behavior.response(opponent, Action.IGNORING);
        assertEquals(behavior.decide(opponent), Action.CLEANING);
        behavior.response(opponent, Action.CLEANING);
        assertEquals(behavior.decide(opponent), Action.IGNORING);
        behavior.response(opponent, Action.CLEANING);
        assertEquals(behavior.decide(opponent), Action.CLEANING);
    }
    
    public Behavior getBehavior() {
        return new FlipFlopper();
    }

    public String behaviorName () {
    	return "Flip-Flopper";
    }
}
