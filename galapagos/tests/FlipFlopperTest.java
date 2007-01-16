package galapagos.tests;

import galapagos.*;


public class FlipFlopperTest extends BehaviorTest {
    public void testDecide()
    {
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

    public void testToString () {
    	assertEquals(behavior.toString(),"Flip-Flopper");
    }
}
