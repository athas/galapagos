package galapagos.tests;

import galapagos.*;


public class CheaterTest extends BehaviorTest {
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
}
